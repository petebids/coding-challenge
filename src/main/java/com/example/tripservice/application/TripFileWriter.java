package com.example.tripservice.application;

import com.example.tripservice.domain.model.Trip;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import javax.money.MonetaryAmount;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TripFileWriter {


    @SneakyThrows
    public void writeTripFile(List<Trip> trips) {
        log.info("writing trips {}", trips);
        final File file = new File("trips.csv");

        final FileWriter fileWriter = new FileWriter(file);


        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount",
                        "CompanyId", "BusID", "PAN", "Status")
                .build();

        try (final CSVPrinter printer = new CSVPrinter(fileWriter, csvFormat)) {
            for (Trip t : trips) {
                printer.printRecord(t.started().toString(), t.finished().toString(), t.durationSecs(), t.fromStopID(), t.toStopId(),
                        formatCharge(t.chargeAmount()), t.companyId(), formatBus(t.busID()), t.pan(), t.status().toString()
                );
            }

        }


    }

    private String formatBus(String busId) {
        // on the input file, buses are "Bus37", in the ouput file it's B37

        return busId.replace("us", "");
    }

    private String formatCharge(MonetaryAmount charge) {

        return "$ " + charge.getNumber();
    }

}
