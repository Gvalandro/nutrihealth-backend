package com.healthnutri.healthnutrition.repository;

import com.healthnutri.healthnutrition.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyRecordRepository extends JpaRepository<DailyRecord,Long> {
    Optional<DailyRecord> findByDate(LocalDate date);

}
