package com.springdemo.capstoneproject1.service;


import com.springdemo.capstoneproject1.model.Match;
import com.springdemo.capstoneproject1.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdShouldReturnMatch() {
        Match mockMatch = new Match();
        mockMatch.setMatchId(1);

        when(matchRepository.findById(1)).thenReturn(Optional.of(mockMatch));

        Optional<Match> result = matchService.findById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getMatchId()).isEqualTo(1);

        verify(matchRepository, times(1)).findById(1);
    }
}