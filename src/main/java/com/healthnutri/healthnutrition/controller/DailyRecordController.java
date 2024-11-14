package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.ComparisonResult;
import com.healthnutri.healthnutrition.dto.FoodDTO;
import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.DailyRecord;
import com.healthnutri.healthnutrition.service.DailyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/daily-records")
public class DailyRecordController {

    @Autowired
    private DailyRecordService dailyRecordService;

    @PostMapping("/add")
    public ResponseEntity<String> addDailyRecord(@RequestBody DailyRecord dailyRecord) {
        NutritionResponse nutritionResponse = dailyRecordService.calculateNutrition(dailyRecord.getDiary());

        double totalCalories = 0;
        double totalSugars = 0;

        if (nutritionResponse != null && nutritionResponse.getFoods() != null) {
            for (FoodDTO food : nutritionResponse.getFoods()) {
                totalCalories += food.getTotalCalories();
                totalSugars += food.getTotalSugar();
            }
        }

        dailyRecord.setTotalCalories(totalCalories);
        dailyRecord.setTotalSugars(totalSugars);

        dailyRecordService.saveDailyRecord(dailyRecord);
        return ResponseEntity.ok("Registro Diário salvo com sucesso!");
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<DailyRecord> getDailyRecordByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<DailyRecord> dailyRecord = dailyRecordService.findByDate(localDate);
        return dailyRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/date/{date}")
    public ResponseEntity<String> updateDailyRecord(@PathVariable String date, @RequestBody DailyRecord updatedDailyRecord) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<DailyRecord> existingRecord = dailyRecordService.findByDate(localDate);

        if (existingRecord.isPresent()) {
            DailyRecord dailyRecord = existingRecord.get();
            dailyRecord.setDiary(updatedDailyRecord.getDiary());
            return ResponseEntity.ok("Registro Diário atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/compare/{userId}")
    public ResponseEntity<ComparisonResult> compareDailyIntakeWithPlan(@PathVariable Long userId, @RequestBody List<FoodDTO> dailyMeals) {
        ComparisonResult result = dailyRecordService.compareDailyIntakeWithPlan(userId, dailyMeals);
        return ResponseEntity.ok(result);
    }
}
