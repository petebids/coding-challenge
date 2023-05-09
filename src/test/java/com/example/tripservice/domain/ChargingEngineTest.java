package com.example.tripservice.domain;

import com.example.tripservice.TestUtils;
import com.example.tripservice.domain.model.Tap;
import com.example.tripservice.domain.model.Trip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ChargingEngineTest {

    @Autowired
    ChargingEngine chargingEngine;


    @Test
    void testHappyPath() {
        final List<Tap> taps = TestUtils.defaultTaps();
        final List<Trip> expected = TestUtils.defaultTrips();


        final List<Trip> trips = chargingEngine.chargeForTaps(taps);

        assertEquals(expected, trips);


    }

}