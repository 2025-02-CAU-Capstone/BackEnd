package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.EmbeddingMetadataRequest;
import com.springdemo.capstoneproject1.dto.EmbeddingResponseDTO;
import com.springdemo.capstoneproject1.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/embeddings")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    /** 1) JSON 저장 */
    @PostMapping("/upload")
    public String uploadMetadata(@RequestBody EmbeddingMetadataRequest req) throws Exception {
        embeddingService.saveMetadata(req);
        return "Metadata saved";
    }

    /** 2) NPY 저장 */
    @PostMapping(value = "/upload-npy", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String uploadNpy(@RequestBody byte[] npy) throws Exception {
        embeddingService.saveNpy(npy);
        return "npy saved";
    }

    /** 3) 최신 임베딩 읽기 */
    @GetMapping("/latest")
    public EmbeddingResponseDTO getLatest() throws Exception {
        return embeddingService.loadLatest();
    }
}
