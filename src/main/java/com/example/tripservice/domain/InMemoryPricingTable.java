package com.example.tripservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import javax.money.MonetaryAmount;


// For this use case this may be a premature abstraction, but for a use case slightly more evolved where
// pricing is dynamic per customer, & imagining where this might serve a back end rest api instead of a cli
// using an interface that can be reimplemented makes sense
@Slf4j
@Component
public class InMemoryPricingTable implements PricingTable {

    private static final PricingRule[] RULES = new PricingRule[]{
            new PricingRule("Company1", "Stop1", "Stop2", Money.of(3.25, "USD")),
            new PricingRule("Company1", "Stop1", null, Money.of(7.30, "USD")),
            new PricingRule("Company1", "Stop2", "Stop3", Money.of(5.50, "USD")),
            new PricingRule("Company1", "Stop2", "Stop1", Money.of(3.25, "USD")),
            new PricingRule("Company1", "Stop3", "Stop2", Money.of(5.50, "USD")),
            new PricingRule("Company1", "Stop1", "Stop3", Money.of(7.30, "USD")),
            new PricingRule("Company1", "Stop3", "Stop1", Money.of(3.25, "USD")),

    };

    @Override
    public MonetaryAmount priceTripByZone(String companyId, String startTripStopId, String endTripStopId) {
        // Given the small scope of data for this use case, a full in memory table scan is fine
        // this could be a composite key lookup if the information was stored in a database or available over a Rest API
        log.info("invoked with {},{},{}", companyId, startTripStopId, endTripStopId);
        if (startTripStopId.equals(endTripStopId)) {
            freeTrip(companyId);
        }

        for (PricingRule rule : RULES) {
            if (rule.companyId.equals(companyId)
                    && rule.startTripStopId.equals(startTripStopId)
                    && (rule.endTripStopId == null && endTripStopId == null) || (rule.endTripStopId.equals(endTripStopId))) {
                return rule.price;
            }
        }
        final String errorMesasge = String.format("no price in table for {}, {}, {}", companyId, startTripStopId, endTripStopId);
        throw new NullPointerException(errorMesasge);
    }

    private record PricingRule(String companyId, String startTripStopId, String endTripStopId, MonetaryAmount price) {
    }
}
