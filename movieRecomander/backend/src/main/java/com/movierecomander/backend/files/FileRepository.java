package com.movierecomander.backend.files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadedFile, Long>, JpaSpecificationExecutor<UploadedFile>  {
}
