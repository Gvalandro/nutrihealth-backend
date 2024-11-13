package com.healthnutri.healthnutrition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodDTO {
    @JsonProperty("food_name")
    private String name;
    @JsonProperty("nf_calories")
    private double totalCalories;
    @JsonProperty("nf_sugars")
    private double totalSugar;

    public FoodDTO(String name, double totalCalories, double totalSugar) {
        this.name = name;
        this.totalCalories = totalCalories;
        this.totalSugar = totalSugar;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public double getTotalSugar() {
        return totalSugar;
    }

    public void setTotalSugar(double totalSugar) {
        this.totalSugar = totalSugar;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", totalCalories=" + totalCalories +
                ", totalSugar=" + totalSugar +
                '}';
    }
}
