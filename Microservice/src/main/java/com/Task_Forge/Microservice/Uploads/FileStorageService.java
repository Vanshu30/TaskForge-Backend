package com.Task_Forge.Microservice.Uploads;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException{
        //Create upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        //Generate a unique filename
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;

        //Get file path and save
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }
}