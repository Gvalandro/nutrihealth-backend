package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.Meal;
import com.healthnutri.healthnutrition.model.NutritionPlan;
import com.healthnutri.healthnutrition.repository.NutritionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionPlanService {

    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    private NutritionApiService nutritionApiService;

    public void savePlan(NutritionPlan nutritionPlan) {
        NutritionResponse nutritionResponse = calculatePlanNutrition(nutritionPlan.getPlan());
        nutritionPlan.setTotalCalories(nutritionResponse.getTotalCalories());
        nutritionPlan.setTotalSugars(nutritionResponse.getTotalSugars());
        nutritionPlanRepository.save(nutritionPlan);
    }

    public void updatePlan(NutritionPlan nutritionPlan) {
        NutritionResponse nutritionResponse = calculatePlanNutrition(nutritionPlan.getPlan());
        nutritionPlan.setTotalCalories(nutritionResponse.getTotalCalories());
        nutritionPlan.setTotalSugars(nutritionResponse.getTotalSugars());
        nutritionPlanRepository.save(nutritionPlan);
    }

    public NutritionResponse calculatePlanNutrition(List<Meal> meals) {
        double totalCalories = 0;
        double totalSugars = 0;

        for (Meal meal : meals) {
            String query = meal.getQuantityInGrams() + " grams of " + meal.getFoodItem();
            NutritionResponse nutritionResponse = nutritionApiService.getNutritionData(query);
            if (nutritionResponse != null) {
                totalCalories += nutritionResponse.getTotalCalories();
                totalSugars += nutritionResponse.getTotalSugars();
            }
        }

        NutritionResponse aggregatedResponse = new NutritionResponse();
        aggregatedResponse.setTotalCalories(totalCalories);
        aggregatedResponse.setTotalSugars(totalSugars);
        return aggregatedResponse;
    }
    public Optional<NutritionPlan> findByUserId(Long userId) {
        return nutritionPlanRepository.findByUserId(userId);
    }
}
