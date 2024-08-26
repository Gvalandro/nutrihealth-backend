package com.healthnutri.healthnutrition;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class testNutritionAPI {


    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testNutritionixApi() {
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-app-id", "a8c58933");
        headers.set("x-app-key", "26c79d24a1d96c75c6ed0dc8199f1830");

        String body = "{\"query\": \"1000g of GRaper\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        System.out.println(response.getBody());
    }
}
