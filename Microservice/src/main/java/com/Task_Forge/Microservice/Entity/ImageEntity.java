package com.Task_Forge.Microservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String path;

    public ImageEntity(String path){
        this.path = path;
    }

}