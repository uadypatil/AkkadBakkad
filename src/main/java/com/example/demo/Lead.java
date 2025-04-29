package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Lead {

    private static final String FILE_NAME = "data.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File externalFile;

    // Copy data.json from classpath to a temp file on disk (only needed once)
    static {
        try {
            ClassPathResource resource = new ClassPathResource(FILE_NAME);
            File temp = File.createTempFile("data", ".json");
            temp.deleteOnExit();
            try (InputStream is = resource.getInputStream()) {
                Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            externalFile = temp;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load lead file", e);
        }
    }

    // Read lead count from JSON
    public static int count() {
        try {
            LeadData data = mapper.readValue(externalFile, LeadData.class);
            return data.getLead();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Increase lead count and update JSON
    public static void increase() {
        int lead = count();
        lead++;
        saveLead(lead);
    }

    // Reset lead count to zero
    public static void setToZero() {
        saveLead(0);
    }

    // Write lead count to JSON file
    private static void saveLead(int leadValue) {
        try {
            LeadData data = new LeadData();
            data.setLead(leadValue);
            mapper.writerWithDefaultPrettyPrinter().writeValue(externalFile, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
