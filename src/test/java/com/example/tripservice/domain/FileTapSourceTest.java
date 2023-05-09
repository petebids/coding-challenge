package com.example.tripservice.domain;

import com.example.tripservice.TestUtils;
import com.example.tripservice.domain.model.Tap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTapSourceTest {


    final FileTapSource tapfileParserFIle = new FileTapSource();

    @Test
    void testValidFileParsedSuccessfully() {
        //given a valid input file
        final String INPUT_CSV = "input.csv";

        //when tap file parsed
        final List<Tap> taps = tapfileParserFIle.parseFromFilePath(INPUT_CSV).orElseThrow(NullPointerException::new);

        //then expected data is extracted
        final List<Tap> expected = TestUtils.defaultTaps();


        assertEquals(expected, taps);
    }

    @Test
    void testNullThrowsIllegalArguement() {
        assertThrows(IllegalArgumentException.class,
                () -> tapfileParserFIle.parseFromFilePath(null));
    }


}