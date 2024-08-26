package com.healthnutri.healthnutrition.dto;

import com.healthnutri.healthnutrition.model.User;

public class UserConverter {

    public static UserDTO userDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNutritionPlan(user.getNutritionPlan());
        userDTO.setMeals(user.getMeals());
        userDTO.setDailyRecords(user.getDailyRecords());

        return userDTO;
    }

}
