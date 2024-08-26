package com.healthnutri.healthnutrition.repository;

import com.healthnutri.healthnutrition.model.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan,Long> {
    Optional<NutritionPlan> findByUserId(Long userID);
}
