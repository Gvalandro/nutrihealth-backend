package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.NutritionPlan;
import com.healthnutri.healthnutrition.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/nutritionPlan")
public class NutritionPlanController {

    @Autowired
    private NutritionService nutritionService;



    @PostMapping("/add")
    public ResponseEntity<String> addNutritionPlan(@RequestBody NutritionPlan nutritionPlan) {
        NutritionResponse nutritionResponse = nutritionService.calculateNutrition(nutritionPlan.getPlan());
        nutritionPlan.setCreatedAt(LocalDateTime.now());
        nutritionPlan.setTotalSugars(nutritionResponse.getTotalSugars());
        nutritionPlan.setTotalCalories(nutritionResponse.getTotalCalories());
        nutritionService.savePlan(nutritionPlan);
        return ResponseEntity.ok("Plano Alimentar salvo com sucesso!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<NutritionPlan> getNutritionPlanByUserId(@PathVariable Long userId) {
        Optional<NutritionPlan> nutritionPlan = nutritionService.findByUserId(userId);
        return nutritionPlan.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNutritionPlan(@RequestBody NutritionPlan nutritionPlan) {
        NutritionResponse nutritionResponse = nutritionService.calculateNutrition(nutritionPlan.getPlan());
        nutritionPlan.setCreatedAt(LocalDateTime.now());
        nutritionPlan.setTotalCalories(nutritionResponse.getTotalCalories());
        nutritionPlan.setTotalSugars(nutritionResponse.getTotalSugars());
        nutritionService.updatePlan(nutritionPlan);
        return ResponseEntity.ok("Plano Alimentar atualizado com sucesso!");
    }

}
