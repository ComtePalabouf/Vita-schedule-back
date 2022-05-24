package com.kinikun.vitalityschedule.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/matches")
public class MatchesController {

    @Value("${API_TOKEN}")
    private String apiToken;
    
    @CrossOrigin(origins = "https://vitality-schedule.ddns.net/")
    @GetMapping()
    public ResponseEntity<String> getMatches(@RequestParam String[] teamIds) {
        RestTemplate restTemplate = new RestTemplate();

        StringBuilder apiUrl = new StringBuilder("https://api.pandascore.co/matches?filter[opponent_id]=");
        apiUrl.append(String.join(",", teamIds));
        apiUrl.append("&token=");
        apiUrl.append(apiToken);

        return restTemplate.getForEntity(apiUrl.toString(), String.class);
    }
}
