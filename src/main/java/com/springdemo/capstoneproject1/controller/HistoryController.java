package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.HistoryResponse;
import com.springdemo.capstoneproject1.model.History;
import com.springdemo.capstoneproject1.repository.HistoryRepository;
import com.springdemo.capstoneproject1.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryRepository historyRepository;
    private final HistoryService historyService;

    @GetMapping
    public List<HistoryResponse> getAllHistory() {
        return historyService.getHistoryResponses();
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable Long id) {
        historyRepository.deleteById(id);
    }

    @DeleteMapping
    public void clearHistory() {
        historyRepository.deleteAll();
    }
}
