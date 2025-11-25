package com.springdemo.capstoneproject1.dto;

import lombok.Data;

import java.util.List;

@Data
public class TextBox {
    private String text;
    private double confidence;
    private List<List<Integer>> bbox;  // [[x1,y1],[x2,y2] ...]
}
