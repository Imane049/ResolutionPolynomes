package org.example.geminiresolution.Models;

public class GeminiResponse {
    private String text;

    public GeminiResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
