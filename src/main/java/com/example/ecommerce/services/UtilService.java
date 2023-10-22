package com.example.ecommerce.services;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UtilService {
    public static String generateRandomUserID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public static String objectToString(Object obj) {
        // Use String.format to convert the object to a string
        return String.format("ObjectToString: %s", obj.toString());
    }


}
