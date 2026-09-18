package com.romeo.imagefiltertool.service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFilterService {
public String applyFilter(MultipartFile file, String filterName) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        if (originalImage == null) {
            throw new IllegalArgumentException("Le fichier fourni n'est pas une image valide.");
        }

        BufferedImage processedImage = switch (filterName.toLowerCase()) {
            default -> originalImage;
        };

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(processedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }
    
}
