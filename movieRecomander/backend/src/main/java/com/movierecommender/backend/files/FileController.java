package com.movierecommender.backend.files;


import com.movierecommender.backend.BackendConfig;
import com.movierecommender.backend.advice.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;
    private final BackendConfig backendConfig;

    @Autowired
    public FileController(FileService fileService, BackendConfig backendConfig) {
        this.fileService = fileService;
        this.backendConfig = backendConfig;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<FileCreatedInfo> upload(@RequestParam("file")MultipartFile multipartFile) {
        String fileId = fileService.upload(multipartFile);
        String fileDownloadLink = this.backendConfig.getSelfURI() + "/api/v1/files/" + fileId;

        try {
            URI currentLocation = new URI(backendConfig.getSelfURI() + "/api/v1/files");
            
            URI fileDownloadLinkUri = new URI(fileDownloadLink);
            var fileCreatedInfo = new FileCreatedInfo(fileId, fileDownloadLinkUri);

            return ResponseEntity.created(currentLocation).body(fileCreatedInfo);
        }
        catch (URISyntaxException exception) {
            throw new BusinessException("Failed to obtain valid download uri",
                    "File storage error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fileIdName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Resource> download(@PathVariable String fileIdName) {
        Optional<UploadedFile> foundFileOnServer = fileService.download(fileIdName);

        if (foundFileOnServer.isEmpty()) {
            throw new BusinessException("File not found", "Invalid data", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFileOnServer.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + foundFileOnServer.get().getFileName())
                .body(new ByteArrayResource(foundFileOnServer.get().getFileData()));
    }

    @DeleteMapping("/{fileIdName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void delete(@PathVariable String fileIdName) { fileService.delete(fileIdName); }
}
