package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.EmbeddingMetadataRequest;
import com.springdemo.capstoneproject1.dto.EmbeddingResponseDTO;
import com.springdemo.capstoneproject1.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

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

    /** 2) NPY 저장 (스트리밍 방식으로 변경) */
    @PostMapping(value = "/upload-npy", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String uploadNpy(InputStream inputStream) throws Exception {
        embeddingService.saveNpyStream(inputStream);
        return "npy saved";
    }

    /** 3) 최신 임베딩 읽기 */
    @GetMapping("/latest")
    public EmbeddingResponseDTO getLatest() throws Exception {
        return embeddingService.loadLatest();
    }

    @GetMapping(value = "/latest-npy", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> downloadNpy() throws Exception {

        InputStream stream = embeddingService.loadNpyStream();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @DeleteMapping("/delete")
    public String deleteEmbeddings() throws Exception {
        embeddingService.deleteLatest();
        return "Embedding files deleted";
    }
}
