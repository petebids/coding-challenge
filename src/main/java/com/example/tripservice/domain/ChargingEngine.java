package com.example.tripservice.domain;

import com.example.tripservice.domain.mapper.TapMapper;
import com.example.tripservice.domain.model.Tap;
import com.example.tripservice.domain.model.TapType;
import com.example.tripservice.domain.model.Trip;
import com.example.tripservice.domain.model.TripStatus;
import com.example.tripservice.infrastructure.TapEntity;
import com.example.tripservice.infrastructure.TapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChargingEngine {


    private final TapRepository tapRepository;
    private final TapMapper tapMapper;
    private final PricingTable pricingTable;


    public List<Trip> chargeForTaps(final List<Tap> taps) {

        if (taps == null) {
            throw new IllegalArgumentException("no taps in file !");

        }

        tapRepository.deleteAll();
        tapRepository.flush();


        final List<TapEntity> entities = taps.stream()
                .map(tapMapper::toEntity)
                .toList();

        tapRepository.saveAll(entities);


        final List<Tap> tapOns = taps.stream()
                .filter(tap -> TapType.ON.equals(tap.tapType()))
                .toList();

        // for each tap , find a matching tap off
        return tapOns.stream().map(on -> {
            // first look for a cancelled trip
            final Optional<Tap> cancelled = tapRepository.findTapOffForCancelledTrip(on.dateTime().toLocalDate(), on.companyId(), on.pan(), on.stopId(), on.dateTime()).map(tapMapper::fromEntity);

            if (cancelled.isPresent()) {

                final Tap off = cancelled.get();

                return Trip.builder()
                        .started(on.dateTime())
                        .finished(off.dateTime())
                        .durationSecs(durationSecs(on.dateTime(), off.dateTime()))
                        .companyId(on.companyId())
                        .fromStopID(on.stopId())
                        .toStopId(off.stopId())
                        .chargeAmount(pricingTable.freeTrip(on.companyId()))
                        .status(TripStatus.CANCELLED)
                        .build();

            }

            // then look for a completed trip
            final Optional<Tap> matchingCompletedTrip = tapRepository.findMatchingCompletedTrip(on.dateTime().toLocalDate(), on.companyId(), on.pan(), on.dateTime()).map(tapMapper::fromEntity);

            if (matchingCompletedTrip.isPresent()) {

                Tap off = matchingCompletedTrip.get();


                final MonetaryAmount amount = pricingTable.priceTripByZone(on.companyId(), on.stopId(), off.stopId());

                return Trip.builder()
                        .companyId(on.companyId())
                        .chargeAmount(amount)
                        .started(on.dateTime())
                        .finished(off.dateTime())
                        .durationSecs(durationSecs(on.dateTime(), off.dateTime()))
                        .busID(on.busId())
                        .pan(on.pan())
                        .fromStopID(on.stopId())
                        .toStopId(off.stopId())
                        .status(TripStatus.COMPLETED)
                        .build();
            }

            // if neither can be found,

            final MonetaryAmount chargeAmount = pricingTable.priceTripByZone(on.companyId(), on.stopId(), null);

            return Trip.builder()
                    .started(on.dateTime())
                    .fromStopID(on.stopId())
                    .chargeAmount(chargeAmount)
                    .companyId(on.companyId())
                    .busID(on.busId())
                    .pan(on.pan())
                    .status(TripStatus.INCOMPLETE)
                    .build();

        }).toList();


    }

    // TODO fix
    private long durationSecs(LocalDateTime start, LocalDateTime end) {

        return ChronoUnit.SECONDS.between(start, end);
    }


}
