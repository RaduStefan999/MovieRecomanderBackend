package com.movierecommender.backend.files;

import java.net.URI;

public class FileCreatedInfo {
    String fileId;
    URI fileDownloadLink;

    public FileCreatedInfo(String fileId, URI fileDownloadLink) {
        this.fileId = fileId;
        this.fileDownloadLink = fileDownloadLink;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public URI getFileDownloadLink() {
        return fileDownloadLink;
    }

    public void setFileDownloadLink(URI fileDownloadLink) {
        this.fileDownloadLink = fileDownloadLink;
    }
}
