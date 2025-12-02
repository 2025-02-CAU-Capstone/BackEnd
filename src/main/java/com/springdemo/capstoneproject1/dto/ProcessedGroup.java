package com.springdemo.capstoneproject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ProcessedGroup {

    @JsonProperty("choose_id")
    private int chooseId;

    @JsonProperty("group_position")
    private List<List<Float>> groupPosition;

    @JsonProperty("merged_text")
    private String mergedText;
}