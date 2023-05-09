package com.example.tripservice.infrastructure;

import com.example.tripservice.domain.model.TapType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class TapEntity {

    @Getter
    @Id
    private String id;

    private LocalDateTime dateTime;

    private LocalDate localDate;

    private TapType tapType;

    private String stopId;

    private String companyId;

    private String busId;

    private String pan;


}
