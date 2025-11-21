package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.ChapterDTO;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;
    private final ModelMapper modelMapper;

    // 전체 조회 (by order)
    @GetMapping
    public List<ChapterDTO> getAll() {
        return chapterService.findAll()
                .stream()
                .map(ch -> modelMapper.map(ch, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ChapterDTO getById(@PathVariable Integer id) {
        Chapter chapter = chapterService.findById(id).orElse(null);
        return chapter != null ? modelMapper.map(chapter, ChapterDTO.class) : null;
    }

    // 생성
    @PostMapping
    public ChapterDTO create(@RequestBody ChapterDTO dto) {
        Chapter chapter = modelMapper.map(dto, Chapter.class);
        Chapter saved = chapterService.save(chapter);
        return modelMapper.map(saved, ChapterDTO.class);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        chapterService.delete(id);
    }
}
