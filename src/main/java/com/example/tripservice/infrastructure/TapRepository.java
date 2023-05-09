package com.example.tripservice.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TapRepository extends JpaRepository<TapEntity, String> {


    @Query("select t from TapEntity t " +
            "where t.tapType = com.example.tripservice.domain.model.TapType.OFF " +
            "and t.localDate = :date " +
            "and t.companyId = :companyId " +
            "and t.pan = :pan  " +
            "and t.stopId = :stopId " +
            "and t.dateTime > :start " +
            "order by t.dateTime " +
            "asc limit 1")
    Optional<TapEntity> findTapOffForCancelledTrip(LocalDate date , String companyId, String pan, String stopId, LocalDateTime start);

    @Query("select t from TapEntity  t " +
            "where  t.tapType = com.example.tripservice.domain.model.TapType.OFF " +
            "and t.localDate = :date " +
            "and t.companyId = :companyId " +
            "and t.pan = :pan " +
            "and t.dateTime > :start " +
            "order by t.dateTime " +
            "asc limit 1 ")
    Optional<TapEntity> findMatchingCompletedTrip(LocalDate date , String companyId, String pan, LocalDateTime start);

}
