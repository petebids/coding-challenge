package com.example.tripservice;

import com.example.tripservice.domain.model.Tap;
import com.example.tripservice.domain.model.TapType;
import com.example.tripservice.domain.model.Trip;
import com.example.tripservice.domain.model.TripStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    private static final LocalDateTime started = LocalDateTime.of(2018, Month.JANUARY, 22, 13, 00);
    private static final LocalDateTime ended = LocalDateTime.of(2018, Month.JANUARY, 22, 13, 05);

    public static List<Tap> defaultTaps() {

        final List<Tap> expected = new ArrayList<>();


        expected.add(new Tap("1",
                started,
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));

        expected.add(new Tap("2",
                ended,
                TapType.OFF,
                "Stop2",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));

        return expected;
    }

    public static List<Trip> defaultTrips() {
        // in the example it says the duration secs should be 900 but that's 15 minutes not 5
        return Collections.singletonList(new Trip(started, ended, 300, "Stop1", "Stop2", Money.of(3.25, "USD"), "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED));
    }
}
