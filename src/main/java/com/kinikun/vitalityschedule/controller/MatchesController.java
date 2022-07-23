package com.kinikun.vitalityschedule.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/matches")
public class MatchesController {

    @Value("${API_TOKEN}")
    private String apiToken;
    
    @GetMapping()
    public ResponseEntity<String> getMatches(@RequestParam String[] teamIds) {
        RestTemplate restTemplate = new RestTemplate();

        LocalDateTime startFilterDate = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        LocalDateTime endFilterDate = LocalDateTime.now().plus(1, ChronoUnit.YEARS);

        StringBuilder apiUrl = new StringBuilder("https://api.pandascore.co/matches?per_page=100&filter[opponent_id]=");
        apiUrl.append(String.join(",", teamIds));
        apiUrl.append("&range[begin_at]=");
        apiUrl.append(startFilterDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        apiUrl.append(",");
        apiUrl.append(endFilterDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        apiUrl.append("&token=");
        apiUrl.append(apiToken);

        return restTemplate.getForEntity(apiUrl.toString(), String.class);
    }
    
    @GetMapping("/{matchId}/opponents")
    public ResponseEntity<String> getMatchOpponents(@PathVariable String matchId) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            StringBuilder apiUrl = new StringBuilder("https://api.pandascore.co/matches/");
            apiUrl.append(matchId);
            apiUrl.append("/opponents?token=");
            apiUrl.append(apiToken);

            return restTemplate.getForEntity(apiUrl.toString(), String.class);
        } catch (NotFound exception) {
            return null;
        }
    }
}
