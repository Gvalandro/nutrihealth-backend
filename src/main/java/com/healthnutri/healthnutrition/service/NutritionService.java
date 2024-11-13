package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.ComparisonResult;
import com.healthnutri.healthnutrition.dto.FoodDTO;
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
import java.util.ArrayList;
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
    private NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private RestTemplate restTemplate;

    private double totalCalories = 0;
    private double totalSugars = 0;

/*
    public NutritionResponse calculateNutrition(Meal meal) {
        String query = meal.getQuantityInGrams() + " grams of " + meal.getFoodItem();
        //System.out.println(query);
        return getNutritionData(query);
    }
*/
public NutritionResponse calculateNutrition(List<Meal> foodItems) {
    List<FoodDTO> mealsWithNutrition = new ArrayList<>();
    double totalCalories = 0;
    double totalSugars = 0.0;

    for (Meal meal : foodItems) {
        String query = meal.getQuantityInGrams() + " grams of " + meal.getFoodItem();
        NutritionResponse nutritionResponse = getNutritionData(query);

        if (nutritionResponse != null && nutritionResponse.getFoods() != null) {
            for (FoodDTO food : nutritionResponse.getFoods()) {
                Meal mealWithNutrition = new Meal(meal.getFoodItem(), meal.getQuantityInGrams());
                mealWithNutrition.setCalories(food.getTotalCalories());
                mealWithNutrition.setNutrients("Sugars: " + food.getTotalSugar());
                mealsWithNutrition.add(new FoodDTO(food.getName(),food.getTotalCalories(),food.getTotalSugar()));

                totalCalories += food.getTotalCalories();
                totalSugars += food.getTotalSugar();
            }
        }
    }

    NutritionResponse response = new NutritionResponse();
    response.setFoods(mealsWithNutrition);
    response.setTotalCalories(totalCalories);
    response.setTotalSugars(totalSugars);

    System.out.println(response.getFoods());
    return response;
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
        //System.out.println(query);
        //System.out.println(response.getBody());
        //System.out.println(response.getHeaders());
        return response.getBody();
    }

    public ComparisonResult compareDailyIntakeWithPlan(Long userId, List<FoodDTO> dailyFoods) {
        Optional<NutritionPlan> optionalPlan = findByUserId(userId);
        if (!optionalPlan.isPresent()) {
            throw new RuntimeException("Plano nutricional não encontrado para o usuário: " + userId);
        }

        NutritionPlan nutritionPlan = optionalPlan.get();

        double totalDailyCalories = dailyFoods.stream().mapToDouble(FoodDTO::getTotalCalories).sum();
        double totalDailySugars = dailyFoods.stream().mapToDouble(FoodDTO::getTotalSugar).sum();

        double planCalories = nutritionPlan.getTotalCalories();
        double planSugars = nutritionPlan.getTotalSugars();

        return new ComparisonResult(totalDailyCalories, planCalories, totalDailySugars, planSugars);
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
