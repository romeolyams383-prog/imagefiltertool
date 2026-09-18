package com.romeo.imagefiltertool.service;

import java.awt.Color;
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
            case "mono" -> applyMono(originalImage);
            case "noir" -> applyNoir(originalImage);
            case "negatif" -> applyNegatif(originalImage);
            case "vintage" -> applyVintage(originalImage);
            case "flou" -> applyFlou(originalImage);
            case "contours" -> applyContours(originalImage);
            default -> originalImage; // "Original"
        };

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(processedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    // 1. Mono (Niveaux de gris standard)
    private BufferedImage applyMono(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));
                // Formule de luminance relative (norme ITU-R BT.601)
                int gray = (int) (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue());
                Color grayColor = new Color(gray, gray, gray);
                result.setRGB(x, y, grayColor.getRGB());
            }
        }
        return result;
    }

    // 2. Noir (Niveaux de gris à fort contraste)
    private BufferedImage applyNoir(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));
                int gray = (int) (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue());
                
                // Accentuation du contraste (seuil dynamique simple)
                int contrastGray = gray < 128 ? Math.max(0, gray - 40) : Math.min(255, gray + 40);
                
                Color noirColor = new Color(contrastGray, contrastGray, contrastGray);
                result.setRGB(x, y, noirColor.getRGB());
            }
        }
        return result;
    }

    // 3. Négatif (Inversion des couleurs)
    private BufferedImage applyNegatif(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));
                Color negColor = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
                result.setRGB(x, y, negColor.getRGB());
            }
        }
        return result;
    }

    // 4. Vintage (Effet Sépia)
    private BufferedImage applyVintage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                // Algorithme standard Sépia
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                Color sepiaColor = new Color(
                        Math.min(255, tr),
                        Math.min(255, tg),
                        Math.min(255, tb)
                );
                result.setRGB(x, y, sepiaColor.getRGB());
            }
        }
        return result;
    }

    // 5. Flou (Convolution par matrice moyenne 3x3)
    private BufferedImage applyFlou(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int r = 0, g = 0, b = 0;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        Color c = new Color(img.getRGB(x + dx, y + dy));
                        r += c.getRed();
                        g += c.getGreen();
                        b += c.getBlue();
                    }
                }
                result.setRGB(x, y, new Color(r / 9, g / 9, b / 9).getRGB());
            }
        }
        return result;
    }

    // 6. Contours (Opérateur de Sobel simple)
    private BufferedImage applyContours(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] gx = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] gy = {
            {-1, -2, -1},
            { 0,  0,  0},
            { 1,  2,  1}
        };

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int valX = 0;
                int valY = 0;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        Color c = new Color(img.getRGB(x + dx, y + dy));
                        int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                        valX += gray * gx[dy + 1][dx + 1];
                        valY += gray * gy[dy + 1][dx + 1];
                    }
                }

                int magnitude = (int) Math.sqrt(valX * valX + valY * valY);
                magnitude = Math.min(255, Math.max(0, magnitude));

                Color edgeColor = new Color(magnitude, magnitude, magnitude);
                result.setRGB(x, y, edgeColor.getRGB());
            }
        }
        return result;
    }
}