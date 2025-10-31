package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.model.Match;
import com.springdemo.capstoneproject1.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Optional<Match> findById(Integer id) {
        return matchRepository.findById(id);
    }

    public Match save(Match match) {
        return matchRepository.save(match);
    }

    public void delete(Integer id) {
        matchRepository.deleteById(id);
    }

    public List<Match> findByProblemId(Integer problemId) {
        return matchRepository.findAll()
                .stream()
                .filter(m -> m.getProblem() != null && m.getProblem().getProblemId().equals(problemId))
                .toList();
    }

    public List<Match> findByChapterId(Integer chapterId) {
        return matchRepository.findAll()
                .stream()
                .filter(m -> m.getChapter() != null && m.getChapter().getChapterId().equals(chapterId))
                .toList();
    }

    public List<Match> findByTranscriptId(Integer transcriptId) {
        return matchRepository.findAll()
                .stream()
                .filter(m -> m.getTranscript() != null && m.getTranscript().getTranscriptId().equals(transcriptId))
                .toList();
    }

}
