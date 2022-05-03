package com.movierecomander.backend.files;

import org.springframework.beans.factory.annotation.Autowired;
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
            Path filePathOnServer = Paths.get(fileConfig.getFileStorePath() + timestamp);
            Files.write(filePathOnServer, data);

            UploadedFile uploadedFile = new UploadedFile(filePathOnServer.toString(), "file" + timestamp,
                    file.getName(), file.getContentType());

            fileRepository.save(uploadedFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<UploadedFile> download(String fileIdName) {
        return fileRepository.findByFileIdName(fileIdName).stream().findFirst();
    }

    public void delete(String fileIdName) {
        var fileOnServer = fileRepository.findByFileIdName(fileIdName).stream().findFirst();

        if (fileOnServer.isPresent()) {
            fileOnServer.get().deleteFileData();
            fileRepository.delete(fileOnServer.get());
        }
    }
}
