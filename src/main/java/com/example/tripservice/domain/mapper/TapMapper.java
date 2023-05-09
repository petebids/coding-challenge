package com.example.tripservice.domain.mapper;

import com.example.tripservice.domain.model.Tap;
import com.example.tripservice.infrastructure.TapEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TapMapper {

    @Mapping(source = "dateTime", target = "localDate")
    TapEntity toEntity(Tap tap);

    Tap fromEntity(TapEntity tapEntity);

    default LocalDate todate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }
}
