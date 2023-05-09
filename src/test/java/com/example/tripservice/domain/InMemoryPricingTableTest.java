package com.example.tripservice.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import javax.money.MonetaryAmount;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryPricingTableTest {


    InMemoryPricingTable pricingTable = new
            InMemoryPricingTable();

    @ParameterizedTest
    @CsvSource(value = {
            "Company1,Stop1,Stop2,USD 3.25",
            "Company1,Stop1,,USD 7.3",

    })
    void testExpectedPrices(ArgumentsAccessor argumentsAccessor) {

        String companyId = argumentsAccessor.getString(0);
        String firstStop = argumentsAccessor.getString(1);
        String latStop = argumentsAccessor.getString(2);
        String expectedPrice = argumentsAccessor.getString(3);

        final MonetaryAmount amount = pricingTable.priceTripByZone(companyId, firstStop, latStop);

        assertEquals(expectedPrice, amount.toString());
    }

}