package com.example.tripservice.domain;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public interface PricingTable {

    MonetaryAmount priceTripByZone(String companyId, String startTripStopId, String endTripStopId);

    default CurrencyUnit getCurrencyForCompany(String companyId) {
        return Monetary.getCurrency("USD");
    }

    default MonetaryAmount freeTrip(String companyId) {
        return Monetary.getDefaultAmountFactory()
                .setCurrency(getCurrencyForCompany(companyId))
                .setNumber(0).create();
    }
}
