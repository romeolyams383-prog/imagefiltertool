package com.romeo.imagefiltertool.controller;
import com.romeo.imagefiltertool.dto.FilterResponse;
import com.romeo.imagefiltertool.service.ImageFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/filters")
@CrossOrigin(origins = "*")
public class ImageFilterController {
    private final ImageFilterService imageFilterService;

    public ImageFilterController(ImageFilterService imageFilterService) {
        this.imageFilterService = imageFilterService;
    }

    @PostMapping("/apply")
    public ResponseEntity<FilterResponse> applyFilter(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "filter", defaultValue = "Original") String filter) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String processedBase64 = imageFilterService.applyFilter(file, filter);
            return ResponseEntity.ok(new FilterResponse(processedBase64, filter));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
