package com.healthnutri.healthnutrition.dto;

public class ComparisonResult {
    private double dailyCalories;
    private double planCalories;
    private double dailySugars;
    private double planSugars;

    public ComparisonResult(double dailyCalories, double planCalories, double dailySugars, double planSugars) {
        this.dailyCalories = dailyCalories;
        this.planCalories = planCalories;
        this.dailySugars = dailySugars;
        this.planSugars = planSugars;
    }

    // Getters e Setters
}

