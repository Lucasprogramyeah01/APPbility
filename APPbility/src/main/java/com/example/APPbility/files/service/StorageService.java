package com.example.APPbility.files.service;

import com.example.APPbility.files.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    FileMetadata store(MultipartFile file);

    Resource loadAsResource(String id);

    void deleteFile(String filename);

    void deleteFileInFolder(String folder, String filename);

    FileMetadata storeInFolder(MultipartFile multipartFile, String folder);

}
