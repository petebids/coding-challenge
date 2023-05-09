package com.example.tripservice.domain;

import com.example.tripservice.domain.model.Tap;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

public interface TapSource {
    @SneakyThrows
    Optional<List<Tap>> parseFromFilePath(String path);
}
