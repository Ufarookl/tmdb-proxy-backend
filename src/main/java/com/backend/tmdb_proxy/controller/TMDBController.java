package com.backend.tmdb_proxy.controller;

import com.backend.tmdb_proxy.service.TMDBService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/movies")
public class TMDBController {

    @Autowired
    private TMDBService tmdbService;
    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/popular")
    public ResponseEntity<String> getPopular() {
        return ResponseEntity.ok(tmdbService.fetchFromTMDB("/movie/popular"));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<String> getTopRated() {
        return ResponseEntity.ok(tmdbService.fetchFromTMDB("/movie/top_rated"));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<String> getUpcoming() {
        return ResponseEntity.ok(tmdbService.fetchFromTMDB("/movie/upcoming"));
    }

    @GetMapping("/now-playing")
    public ResponseEntity<String> getNowPlaying() {
        return ResponseEntity.ok(tmdbService.fetchFromTMDB("/movie/now_playing"));
    }

    @GetMapping("/trending")
    public ResponseEntity<String> getTrending() {
        return ResponseEntity.ok(tmdbService.fetchFromTMDB("/trending/movie/week"));
    }

    @GetMapping("/poster/**")
    public ResponseEntity<byte[]> getPosterImage(HttpServletRequest request) {
        String fullPath = request.getRequestURI().replace("/api/movies/poster", "");

        String imageUrl = "https://image.tmdb.org/t/p/w500" + fullPath;
        System.out.println(imageUrl);
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(response.getHeaders().getContentType());

            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}