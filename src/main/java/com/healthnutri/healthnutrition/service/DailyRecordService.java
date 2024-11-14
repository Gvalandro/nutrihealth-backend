package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.ComparisonResult;
import com.healthnutri.healthnutrition.dto.FoodDTO;
import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.DailyRecord;
import com.healthnutri.healthnutrition.model.Meal;
import com.healthnutri.healthnutrition.model.NutritionPlan;
import com.healthnutri.healthnutrition.repository.DailyRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DailyRecordService {

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private NutritionApiService nutritionApiService;

    @Autowired
    private NutritionPlanService nutritionPlanService;

    public NutritionResponse calculateNutrition(List<Meal> meals) {
        double totalCalories = 0;
        double totalSugars = 0;
        NutritionResponse aggregatedResponse = new NutritionResponse();
        List<FoodDTO> foods = new ArrayList<>();

        for (Meal meal : meals) {
            String query = meal.getQuantityInGrams() + " grams of " + meal.getFoodItem();

            NutritionResponse nutritionResponse = nutritionApiService.getNutritionData(query);
            if (nutritionResponse != null) {
                foods.addAll(nutritionResponse.getFoods());  // Adiciona os alimentos retornados à lista acumulada
                totalCalories += nutritionResponse.getFoods().get(0).getTotalCalories();
                totalSugars += nutritionResponse.getFoods().get(0).getTotalSugar();
            }
        }

        aggregatedResponse.setFoods(foods);
        aggregatedResponse.setTotalCalories(totalCalories);
        aggregatedResponse.setTotalSugars(totalSugars);
        System.out.println(aggregatedResponse.getFoods().size());
        return aggregatedResponse;
    }

    public void saveDailyRecord(DailyRecord dailyRecord) {
        dailyRecordRepository.save(dailyRecord);
    }

    public Optional<DailyRecord> findByDate(LocalDate date) {
        return dailyRecordRepository.findByDate(date);
    }
    public void updateDailyRecord(DailyRecord dailyRecord) {
        dailyRecordRepository.save(dailyRecord);
    }

    public ComparisonResult compareDailyIntakeWithPlan(Long userId, List<FoodDTO> dailyFoods) {
        Optional<NutritionPlan> optionalPlan = nutritionPlanService.findByUserId(userId);
        if (optionalPlan.isEmpty()) {
            throw new RuntimeException("Plano nutricional não encontrado para o usuário: " + userId);
        }

        NutritionPlan nutritionPlan = optionalPlan.get();

        double totalDailyCalories = dailyFoods.stream().mapToDouble(FoodDTO::getTotalCalories).sum();
        double totalDailySugars = dailyFoods.stream().mapToDouble(FoodDTO::getTotalSugar).sum();

        double planCalories = nutritionPlan.getTotalCalories();
        double planSugars = nutritionPlan.getTotalSugars();

        return new ComparisonResult(totalDailyCalories, planCalories, totalDailySugars, planSugars);
    }
}
