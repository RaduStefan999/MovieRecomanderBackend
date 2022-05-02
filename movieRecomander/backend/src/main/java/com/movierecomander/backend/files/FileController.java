package com.movierecomander.backend.files;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpHeaders;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void upload(@RequestParam("file")MultipartFile multipartFile) {
        fileService.upload(multipartFile);
    }

    @GetMapping("/download/{fileIdName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Resource> download(@PathVariable String fileIdName) {
        Optional<UploadedFile> foundFileOnServer = fileService.download(fileIdName);

        if (foundFileOnServer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFileOnServer.get().getFileType()))
                .header("Content-Disposition", "attachment; filename= "+foundFileOnServer.get().getFileName())
                .body(new ByteArrayResource(foundFileOnServer.get().getFileData()));
    }

    @GetMapping("/delete/{fileIdName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable String fileIdName) {
        fileService.delete(fileIdName);
    }
}
