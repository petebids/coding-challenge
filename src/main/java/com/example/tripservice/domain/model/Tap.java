package com.example.tripservice.domain.model;

import java.time.LocalDateTime;

public record Tap(String id, LocalDateTime dateTime, TapType tapType, String stopId, String companyId, String busId, String pan) {
}
