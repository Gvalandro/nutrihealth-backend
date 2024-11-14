package com.healthnutri.healthnutrition.dto;

public class ComparisonResult {
    private double dailyCalories;
    private double planCalories;
    private double dailySugars;
    private double planSugars;
    private double calorieDifference;
    private double sugarDifference;

    public ComparisonResult(double dailyCalories, double planCalories, double dailySugars, double planSugars) {
        this.dailyCalories = dailyCalories;
        this.planCalories = planCalories;
        this.dailySugars = dailySugars;
        this.planSugars = planSugars;
        updateDifferences();  // Atualiza as diferenças ao criar o objeto
    }

    // Setters apenas para campos alteráveis
    public void setPlanCalories(double planCalories) {
        this.planCalories = planCalories;
        updateDifferences();
    }

    public void setPlanSugars(double planSugars) {
        this.planSugars = planSugars;
        updateDifferences();
    }

    // Getters para todos os campos
    public double getDailyCalories() { return dailyCalories; }
    public double getPlanCalories() { return planCalories; }
    public double getDailySugars() { return dailySugars; }
    public double getPlanSugars() { return planSugars; }
    public double getCalorieDifference() { return calorieDifference; }
    public double getSugarDifference() { return sugarDifference; }

    // Atualiza as diferenças de calorias e açúcar
    private void updateDifferences() {
        this.calorieDifference = dailyCalories - planCalories;
        this.sugarDifference = dailySugars - planSugars;
    }

    @Override
    public String toString() {
        return "ComparisonResult{" +
                "dailyCalories=" + dailyCalories +
                ", planCalories=" + planCalories +
                ", dailySugars=" + dailySugars +
                ", planSugars=" + planSugars +
                ", calorieDifference=" + calorieDifference +
                ", sugarDifference=" + sugarDifference +
                '}';
    }
}

