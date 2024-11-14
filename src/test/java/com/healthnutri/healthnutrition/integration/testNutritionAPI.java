package com.healthnutri.healthnutrition.integration;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import com.healthnutri.healthnutrition.model.Meal;
import com.healthnutri.healthnutrition.repository.MealRepository;
import com.healthnutri.healthnutrition.service.DailyRecordService;
import com.healthnutri.healthnutrition.service.NutritionApiService;
import com.healthnutri.healthnutrition.service.NutritionPlanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class testNutritionAPI {

    @Mock
    private MealRepository mealRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DailyRecordService nutritionService;




    @Test
    public void testNutritionixApi() {
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-app-id", "a8c58933");
        headers.set("x-app-key", "26c79d24a1d96c75c6ed0dc8199f1830");

        String body = "{\"query\": \"200g of rice\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        System.out.println(response.getBody());
    }


    @Test
    public void testListMeals() {
        // Sample list of meals
            Meal food1 = new Meal("Rice", 200);
            Meal food2 = new Meal("Beef", 140);
            Meal food3 = new Meal("Egg", 80);
            //Tudo bem
            List<Meal> meals = new ArrayList<>();
            meals.add(food1);
            meals.add(food2);
            meals.add(food3);


            NutritionResponse response = nutritionService.calculateNutrition(meals);

            System.out.println("NutritionResponse: " + response);
            System.out.println("Total Calories: " + response.getTotalCalories());
            System.out.println("Total Sugars: " + response.getTotalSugars());
            System.out.println("Foods List Size: " + (response.getFoods() != null ? response.getFoods().size() : "null"));

            assertNotNull(response);
            assertNotNull(response.getFoods());
            assertEquals(3, response.getFoods().size());

            assertEquals(737, response.getTotalCalories(), 0.1);
        }

    }




