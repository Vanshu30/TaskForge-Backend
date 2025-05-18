package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.Entity.ImageEntity;
import com.Task_Forge.Microservice.Repository.ImageRepository;
import com.Task_Forge.Microservice.Uploads.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    //API to handle images upload
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image")MultipartFile file){
        try {
            //Save the file and get the file path
            String filePath = fileStorageService.saveFile(file);

            //Create ImageEntity and save it to DB
            ImageEntity image = new ImageEntity(filePath);
            imageRepository.save(image);
            return "Image uploaded successfully" + filePath;
        }catch (Exception e){
            e.printStackTrace();
            return "Image upload failed!";
        }
    }
}