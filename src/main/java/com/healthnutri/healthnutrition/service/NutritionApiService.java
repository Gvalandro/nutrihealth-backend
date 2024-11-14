package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.NutritionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NutritionApiService {

    @Value("${nutritionix.api.url}")
    private String apiUrl;

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.app.key}")
    private String appKey;

    private RestTemplate restTemplate;

    public NutritionApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    protected NutritionResponse getNutritionData(String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);

        String body = "{\"query\":\"" + query + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<NutritionResponse> response = restTemplate.exchange(
                apiUrl + "/v2/natural/nutrients",
                HttpMethod.POST,
                entity,
                NutritionResponse.class
        );
        System.out.println(query);
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
        return response.getBody();
    }


}
