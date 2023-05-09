package com.example.tripservice.domain;

import com.example.tripservice.domain.TapSource;
import com.example.tripservice.domain.model.Tap;
import com.example.tripservice.domain.model.TapType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Used to parse input files into domain objects
 */
@Component
@RequiredArgsConstructor
public class FileTapSource implements TapSource {


    @VisibleForTesting
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    /**
     * @param path path to taps file
     * @return List of taps
     */
    @Override
    @SneakyThrows
    public Optional<List<Tap>> parseFromFilePath(final String path) {
        if (path == null) {
            throw new IllegalArgumentException("File path must not be null");
        }

        final URI uri = getClass().getClassLoader().getResource(path).toURI();
        final File file = new File(uri);
        final Reader csvReader = new FileReader(file);

        final CSVParser csvParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);

        List<Tap> taps;

        try {

            taps = csvParser.stream().map(row -> {

                final String id = row.get(0).strip();
                final String text = row.get(1).strip();
                final LocalDateTime dateTimeUtc = LocalDateTime.parse(text.strip(), DATE_FORMAT);
                final TapType tapType = TapType.valueOf(row.get(2).strip());
                final String stopId = row.get(3).strip();
                final String companyId = row.get(4).strip();
                final String busId = row.get(5).strip();
                final String pan = row.get(6).strip();

                return new Tap(id, dateTimeUtc, tapType, stopId, companyId, busId, pan);
            }).toList();


        } finally {
            csvReader.close();

        }

        return Optional.ofNullable(taps);
    }
}
