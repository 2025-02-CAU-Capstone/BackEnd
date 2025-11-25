package com.springdemo.capstoneproject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TextBox {

    @JsonProperty("box")
    private List<List<Float>> box;

    @JsonProperty("text")
    private String text;

    @JsonProperty("conf")
    private double conf;
}
