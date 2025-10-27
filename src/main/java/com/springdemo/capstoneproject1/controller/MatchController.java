package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.MatchDTO;
import com.springdemo.capstoneproject1.model.Match;
import com.springdemo.capstoneproject1.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final ModelMapper modelMapper;
    @GetMapping
    public List<Match> getAll() {
        return matchService.findAll();
    }

    @GetMapping("/{id}")
    public MatchDTO getById(@PathVariable Integer id) {
        Match match = matchService.findById(id).orElse(null);
        if (match == null) return null;
        return modelMapper.map(match, MatchDTO.class);
    }

    @PostMapping
    public Match create(@RequestBody Match match) {
        return matchService.save(match);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        matchService.delete(id);
    }

    @GetMapping("/problem/{problemId}")
    public List<Match> getByProblem(@PathVariable Integer problemId) {
        return matchService.findByProblemId(problemId);
    }

    @GetMapping("/transcript/{transcriptId}")
    public List<Match> getByTranscript(@PathVariable Integer transcriptId) {
        return matchService.findByTranscriptId(transcriptId);
    }

    @GetMapping("/chapter/{chapterId}")
    public List<Match> getByChapter(@PathVariable Integer chapterId) {
        return matchService.findByChapterId(chapterId);
    }

}
