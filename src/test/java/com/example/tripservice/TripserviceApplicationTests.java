package com.example.tripservice;

import com.example.tripservice.application.TripFileCommandLineRunner;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TripserviceApplicationTests {

    @Autowired
    TripFileCommandLineRunner tripFileCommandLineRunner;

    @SneakyThrows
    @Test
    void e2eTest() {

        tripFileCommandLineRunner.run(new String[]{});
    }

}
