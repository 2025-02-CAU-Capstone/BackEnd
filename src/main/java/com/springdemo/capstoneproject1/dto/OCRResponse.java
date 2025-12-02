package com.springdemo.capstoneproject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OCRResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("textBoxes")
    private List<TextBox> textBoxes;

    @JsonProperty("sentences")
    private List<String> sentences;

    @JsonProperty("raw_text")
    private String rawText;

    @JsonProperty("confidence")
    private double confidence;

    @JsonProperty("imageWidth")
    private int imageWidth;

    @JsonProperty("imageHeight")
    private int imageHeight;

    @JsonProperty("message")
    private String message;

    @JsonProperty("processedGroups")
    private List<ProcessedGroup> processedGroups;
}
