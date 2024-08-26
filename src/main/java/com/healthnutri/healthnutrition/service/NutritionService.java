package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.DailyRecord;
import com.healthnutri.healthnutrition.model.Meal;
import com.healthnutri.healthnutrition.model.NutritionPlan;
import com.healthnutri.healthnutrition.repository.DailyRecordRepository;
import com.healthnutri.healthnutrition.repository.NutritionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class NutritionService {

    @Value("${nutritionix.api.url}")
    private String apiUrl;

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.app.key}")
    private String appKey;

    @Autowired
    NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    DailyRecordRepository dailyRecordRepository;

    private final RestTemplate restTemplate;
    public NutritionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public NutritionResponse calculateNutrition(Meal meal) {
        String query = meal.getQuantityInGrams() + " grams of " + meal.getFoodItem();
        return getNutritionData(query);
    }

    public NutritionResponse calculateNutrition(List<Meal> foodItems) {
        double totalCalories = 0;
        double totalSugars = 0;

        for (Meal meal : foodItems) {
            NutritionResponse nutritionResponse = calculateNutrition(meal);
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
    private NutritionResponse getNutritionData(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);

        String body = "{\"query\":\"" + query + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<NutritionResponse> response = restTemplate.exchange(
                apiUrl + "/v2/natural/nutrients",
                HttpMethod.POST,
                entity,
                NutritionResponse.class
        );

        return response.getBody();
    }

    public void savePlan(NutritionPlan nutritionPlan){
        nutritionPlanRepository.save(nutritionPlan);
    }

    public void saveDailyRecord(DailyRecord dailyRecord){
        dailyRecordRepository.save(dailyRecord);
    }

    public void updatePlan(NutritionPlan nutritionPlan){
        nutritionPlanRepository.save(nutritionPlan);
    }

    public void updateDailyRecord(DailyRecord dailyRecord){
        dailyRecordRepository.save(dailyRecord);
    }

    public Optional<DailyRecord> findByDate(LocalDate date){
        return dailyRecordRepository.findByDate(date);
    }
    public Optional<NutritionPlan> findByUserId(Long userId) {
        return nutritionPlanRepository.findByUserId(userId);
    }
}
