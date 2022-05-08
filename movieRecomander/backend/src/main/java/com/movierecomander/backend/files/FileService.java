package com.movierecomander.backend.files;

import com.movierecomander.backend.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Optional;

@Service
public class FileService {

    FileConfig fileConfig;
    FileRepository fileRepository;

    @Autowired
    public FileService(FileConfig fileConfig, FileRepository fileRepository) {
        this.fileConfig = fileConfig;
        this.fileRepository = fileRepository;
    }

    public void upload(MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            int timestamp = Clock.systemDefaultZone().instant().getNano();
            Path filePathOnServer = Paths.get(fileConfig.getFileStorePath() + "uploaded_" + timestamp);
            Files.write(filePathOnServer, data);

            UploadedFile uploadedFile = new UploadedFile(filePathOnServer.toString(), "file_" + timestamp,
                    file.getName(), file.getContentType());

            fileRepository.save(uploadedFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Specification<UploadedFile> fileFileIDNameContains(String fileIdName) {
        return (uploadFile, cq, cb) -> cb.like(uploadFile.get("fileIdName"), "%" + fileIdName + "%");
    }

    public Optional<UploadedFile> download(String fileIdName) {
        return fileRepository.findAll(fileFileIDNameContains(fileIdName)).stream().findFirst();
    }

    public void delete(String fileIdName) {
        var fileOnServer = fileRepository.findAll(fileFileIDNameContains(fileIdName)).stream().findFirst();

        if (fileOnServer.isPresent()) {
            fileOnServer.get().deleteFileData();
            fileRepository.delete(fileOnServer.get());
        }
    }
}
