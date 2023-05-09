package com.example.tripservice.domain.model;

import lombok.Builder;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;

@Builder
public record Trip(LocalDateTime started, LocalDateTime finished, long durationSecs, String fromStopID, String toStopId,
                   MonetaryAmount chargeAmount, String companyId, String busID, String pan, TripStatus status) {
}
