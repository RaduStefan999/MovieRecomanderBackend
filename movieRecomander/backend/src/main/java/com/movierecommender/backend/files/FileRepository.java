package com.movierecommender.backend.files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<UploadedFile, Long>, JpaSpecificationExecutor<UploadedFile>  {
}
