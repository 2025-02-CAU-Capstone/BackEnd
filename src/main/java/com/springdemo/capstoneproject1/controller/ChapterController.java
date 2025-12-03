package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.ChapterDTO;
import com.springdemo.capstoneproject1.dto.ChapterUpdateRequest;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.repository.LectureRepository;
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
    private final LectureRepository lectureRepository;

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

        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Chapter chapter = new Chapter();
        chapter.setTitle(dto.getTitle());
        chapter.setOrderIndex(dto.getOrderIndex());
        chapter.setUrl(dto.getUrl());
        chapter.setDuration(dto.getDuration());
        chapter.setLecture(lecture);   // ⭐ 반드시 강의 연결

        Chapter saved = chapterService.save(chapter);

        ChapterDTO result = modelMapper.map(saved, ChapterDTO.class);
        result.setLectureId(lecture.getLectureId());

        return result;
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        chapterService.delete(id);
    }

    // 수정
    @PutMapping("/{id}")
    public ChapterDTO update(
            @PathVariable Integer id,
            @RequestBody ChapterUpdateRequest dto) {

        Chapter chapter = chapterService.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found: " + id));

        if (dto.getTitle() != null) chapter.setTitle(dto.getTitle());
        if (dto.getUrl() != null) chapter.setUrl(dto.getUrl());
        if (dto.getOrderIndex() != null) chapter.setOrderIndex(dto.getOrderIndex());
        if (dto.getDuration() != null) chapter.setDuration(dto.getDuration());

        Chapter saved = chapterService.save(chapter);

        return modelMapper.map(saved, ChapterDTO.class);
    }

}
