package com.healthnutri.healthnutrition.model;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "nutrition-plan")
public class NutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Meal> plan;

    private LocalDateTime createdAt;

    @Column
    private double totalCalories;
    @Column
    private double totalSugars;


    // Construtor padrão
    public NutritionPlan() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Meal> getPlan() {
        return plan;
    }

    public void setPlan(List<Meal> plan) {
        this.plan = plan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt= createdAt;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public double getTotalSugars() {
        return totalSugars;
    }

    public void setTotalSugars(double totalSugars) {
        this.totalSugars = totalSugars;
    }
}
