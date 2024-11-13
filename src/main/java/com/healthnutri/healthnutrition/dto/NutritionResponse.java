package com.healthnutri.healthnutrition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NutritionResponse {
    @JsonProperty("foods")
    private List<FoodDTO> foods;
    private double totalCalories;
    private double totalSugars;

    // Getters and setters
    public List<FoodDTO> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodDTO> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "NutritionResponse{" +
                "foods=" + foods +
                '}';
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalSugars(double totalSugars) {
        this.totalSugars = totalSugars;
    }

    public double getTotalSugars() {
        return totalSugars;
    }
}
