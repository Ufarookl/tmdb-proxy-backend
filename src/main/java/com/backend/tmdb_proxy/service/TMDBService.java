package com.backend.tmdb_proxy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TMDBService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchFromTMDB(String path) {
        String url = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println(url);
        System.out.println(entity);
        ResponseEntity<String> response = restTemplate
                .exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());


        return response.getBody();
    }

    public String getMovieVideos(String movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?language=en-US";

        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }

    public String searchMovie(String movieName) {
        String url = "https://api.themoviedb.org/3/search/movie?query=" + movieName +
                "&include_adult=false&language=en-US&page=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }


}