package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.DailyRecord;
import com.healthnutri.healthnutrition.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/daily-records")
public class DailyRecordController {

    @Autowired
    private NutritionService nutritionService;



    @PostMapping("/add")
    public ResponseEntity<String> addDailyRecord(@RequestBody DailyRecord dailyRecord) {
        NutritionResponse nutritionResponse = nutritionService.calculateNutrition(dailyRecord.getDiary());

        dailyRecord.setTotalCalories(nutritionResponse.getTotalCalories());
        dailyRecord.setTotalSugars(nutritionResponse.getTotalSugars());

        nutritionService.saveDailyRecord(dailyRecord);
        return ResponseEntity.ok("Registro Diário salvo com sucesso!");
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<DailyRecord> getDailyRecordByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<DailyRecord> dailyRecord = nutritionService.findByDate(localDate);
        return dailyRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/date/{date}")
    public ResponseEntity<String> updateDailyRecord(@PathVariable String date, @RequestBody DailyRecord updatedDailyRecord) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<DailyRecord> existingRecord = nutritionService.findByDate(localDate);

        if (existingRecord.isPresent()) {
            DailyRecord dailyRecord = existingRecord.get();
            dailyRecord.setDiary(updatedDailyRecord.getDiary());
            nutritionService.updateDailyRecord(dailyRecord);
            return ResponseEntity.ok("Registro Diário atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}




