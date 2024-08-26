package com.healthnutri.healthnutrition.repository;

import com.healthnutri.healthnutrition.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
