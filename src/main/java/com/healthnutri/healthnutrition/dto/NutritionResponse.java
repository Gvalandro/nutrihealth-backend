package com.healthnutri.healthnutrition.dto;

public class NutritionResponse {

    private double totalCalories;
    private double totalSugar;


    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalSugars(double totalSugar) {
        this.totalSugar = totalSugar;
    }


    public double getTotalCalories() {
        return totalCalories;
    }

    public double getTotalSugars() {
        return totalSugar;
    }
}
