package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.NutritionPlan;
import com.healthnutri.healthnutrition.service.NutritionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/nutritionPlan")
public class NutritionPlanController {

    @Autowired
    private NutritionPlanService nutritionService;

    @PostMapping("/add")
    public ResponseEntity<String> addNutritionPlan(@RequestBody NutritionPlan nutritionPlan) {
        NutritionResponse nutritionResponse = nutritionService.calculatePlanNutrition(nutritionPlan.getPlan());
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
    public ResponseEntity<String> updateNutritionPlan(@PathVariable Long id,@RequestBody NutritionPlan nutritionPlan) {
        Optional<NutritionPlan> existingPlan = nutritionService.findByUserId(id);
        if (!existingPlan.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        NutritionResponse nutritionResponse = nutritionService.calculatePlanNutrition(nutritionPlan.getPlan());

        NutritionPlan updatePlan = existingPlan.get();

        updatePlan.setTotalCalories(nutritionResponse.getTotalCalories());
        updatePlan.setTotalSugars(nutritionResponse.getTotalSugars());
        nutritionService.updatePlan(updatePlan);
        return ResponseEntity.ok("Plano Alimentar atualizado com sucesso!");
    }

}
