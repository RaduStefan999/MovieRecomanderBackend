package com.movierecommender.backend.files;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity
public class UploadedFile {
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;
    private String fileIdName;
    private String filePathOnServer;
    private String fileName;
    private String fileType;

    public UploadedFile() {
    }

    public UploadedFile(String filePathOnServer, String fileIdName, String fileName, String fileType) {
        this.filePathOnServer = filePathOnServer;
        this.fileIdName = fileIdName;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public UploadedFile(Long id, String filePathOnServer, String fileIdName, String fileName, String fileType) {
        this.id = id;
        this.filePathOnServer = filePathOnServer;
        this.fileIdName = fileIdName;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePathOnServer() {
        return filePathOnServer;
    }

    public void setFilePathOnServer(String filePathOnServer) {
        this.filePathOnServer = filePathOnServer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileIdName() {
        return fileIdName;
    }

    public void setFileIdName(String fileIdName) {
        this.fileIdName = fileIdName;
    }

    public byte[] getFileData() {
        try
        {
            return Files.readAllBytes(Paths.get(this.filePathOnServer));
        } catch (IOException e)
        {
            return new byte[0];
        }

    }

    public void deleteFileData() {

        try
        {
            Files.delete(Paths.get(this.filePathOnServer));
        } catch (IOException ignored)
        {}
    }
}
