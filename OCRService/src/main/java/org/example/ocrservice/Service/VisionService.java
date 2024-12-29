package org.example.ocrservice.Service;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VisionService {

    @Value("${google.vision.api.token}")
    private String apiToken;

    public String detectText(MultipartFile imageFile) throws IOException {
        try {
            // Configure API client with the token
            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                    .setHeaderProvider(FixedHeaderProvider.create("Authorization", "Bearer " + apiToken))
                    .build();

            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(settings)) {
                // Convert image to ByteString
                ByteString imgBytes = ByteString.copyFrom(imageFile.getBytes());

                // Build image and request
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();

                // Perform text detection
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
                AnnotateImageResponse res = response.getResponses(0);

                if (res.hasError()) {
                    throw new IOException("Google Vision API Error: " + res.getError().getMessage());
                }

                // Extract text and replace `²` with `^2`
                String detectedText = res.getFullTextAnnotation().getText();
                return detectedText.replace("²", "^2");
            }
        } catch (Exception e) {
            throw new IOException("Error connecting to Google Vision API: " + e.getMessage());
        }
    }
}
