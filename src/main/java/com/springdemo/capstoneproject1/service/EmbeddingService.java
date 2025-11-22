package com.springdemo.capstoneproject1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdemo.capstoneproject1.dto.EmbeddingMetadataRequest;
import com.springdemo.capstoneproject1.dto.EmbeddingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_PATH = "/home/ubuntu/embedding_files";
    private final String META_JSON = BASE_PATH + "/latest.json";
    private final String EMBEDDING_NPY = BASE_PATH + "/embeddings.npy";

    public void saveMetadata(EmbeddingMetadataRequest req) throws Exception {
        File dir = new File(BASE_PATH);
        if (!dir.exists()) dir.mkdirs();

        objectMapper.writeValue(new File(META_JSON), req);
    }

    public void saveNpy(byte[] npyBytes) throws Exception {
        Files.write(Paths.get(EMBEDDING_NPY), npyBytes);
    }

    // NPY → double[][] 변환
    private double[][] loadNpyAsDoubleArray() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(EMBEDDING_NPY));
        return npyToDoubleArray(bytes);
    }

    /** 최소 구현: numpy npy to double[][] (float32 assumed) */
    private double[][] npyToDoubleArray(byte[] npyBytes) {
        // 아주 단순 float32 row-major npy 파싱 (최소 구현)
        // 실제 프로덕션에서는 npy-parser 라이브러리 추천

        // ===== HEADER 파싱 =====
        String header = new String(npyBytes, 10, 200);
        int start = header.indexOf('(');
        int end = header.indexOf(')');
        String[] parts = header.substring(start + 1, end).split(",");

        int rows = Integer.parseInt(parts[0].trim());
        int cols = Integer.parseInt(parts[1].trim());

        double[][] arr = new double[rows][cols];

        int offset = header.indexOf("]\n") + 2;  // data 시작점
        int idx = offset;

        // float32 → 4 bytes
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int bits =
                        (npyBytes[idx] & 0xFF) |
                                ((npyBytes[idx+1] & 0xFF) << 8) |
                                ((npyBytes[idx+2] & 0xFF) << 16) |
                                ((npyBytes[idx+3] & 0xFF) << 24);
                arr[r][c] = Float.intBitsToFloat(bits);
                idx += 4;
            }
        }

        return arr;
    }

    public EmbeddingResponseDTO loadLatest() throws Exception {
        EmbeddingMetadataRequest meta =
                objectMapper.readValue(new File(META_JSON), EmbeddingMetadataRequest.class);

        byte[] npyBytes = Files.readAllBytes(Paths.get("/home/ubuntu/embeddings/latest.npy"));
        String b64 = Base64.getEncoder().encodeToString(npyBytes);

        return new EmbeddingResponseDTO(
                meta.getTimestamp(),
                meta.getText(),
                meta.getLectureId(),
                meta.getChapterId(),
                b64
        );
    }
}
