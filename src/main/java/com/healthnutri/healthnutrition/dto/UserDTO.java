package com.healthnutri.healthnutrition.dto;

import com.healthnutri.healthnutrition.model.DailyRecord;
import com.healthnutri.healthnutrition.model.Meal;
import com.healthnutri.healthnutrition.model.NutritionPlan;

import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private NutritionPlan nutritionPlan;
    private List<Meal> meals;
    private List<DailyRecord> dailyRecords;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NutritionPlan getNutritionPlan() {
        return nutritionPlan;
    }

    public void setNutritionPlan(NutritionPlan nutritionPlan) {
        this.nutritionPlan = nutritionPlan;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<DailyRecord> getDailyRecords() {
        return dailyRecords;
    }

    public void setDailyRecords(List<DailyRecord> dailyRecords) {
        this.dailyRecords = dailyRecords;
    }
}
