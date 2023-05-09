package com.example.tripservice.application;

import com.example.tripservice.domain.ChargingEngine;
import com.example.tripservice.domain.FileTapSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TripFileCommandLineRunner implements CommandLineRunner {


    private final FileTapSource fileTapSource;
    private final ChargingEngine chargingEngine;
    private final TripFileWriter tripFileWriter;


    @Override
    public void run(String... args) throws Exception {
        String filename ;

        if (args.length < 1){
            log.info("proceeding with default file name");
            filename = "input.csv";
        }
        else {
            // this case is untested, ran out of time
            filename = args[0];
        }

        fileTapSource.parseFromFilePath(filename).map(chargingEngine::chargeForTaps)
                .ifPresentOrElse(tripFileWriter::writeTripFile, () -> log.error("tap"));


    }
}
