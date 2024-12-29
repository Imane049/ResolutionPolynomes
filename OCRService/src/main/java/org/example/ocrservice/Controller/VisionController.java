package org.example.ocrservice.Controller;


import org.example.ocrservice.Service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/vision")
public class VisionController {

    @Autowired
    private VisionService visionService;

    @PostMapping("/detect-text")
    public ResponseEntity<String> detectText(@RequestParam("image") MultipartFile image) {
        try {
            String result = visionService.detectText(image);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
        }
    }
}
