package com.Task_Forge.Microservice.Util;

import java.util.UUID;

public class UUIDUtils {

    public static boolean isValidUUID(String uuid){
        try {
            UUID.fromString(uuid);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public static UUID convertToUUID(String uuid){
        if(!isValidUUID(uuid)){
            throw new IllegalArgumentException("Invalid UUID format: " + uuid);
        }
        return UUID.fromString(uuid);
    }
}
