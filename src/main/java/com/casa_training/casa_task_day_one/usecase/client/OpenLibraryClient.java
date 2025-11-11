package com.casa_training.casa_task_day_one.usecase.client;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.OpenLibrarySearchResDto;
import com.casa_training.casa_task_day_one.presentation.rest.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenLibraryClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String OPEN_LIBRARY_URL = "https://openlibrary.org/search.json";

    public OpenLibrarySearchResDto fetchBooksByQuery(String query) {

        String url = UriComponentsBuilder
                .fromHttpUrl(OPEN_LIBRARY_URL)
                .queryParam("q", query)
                .toUriString();

        try {
            return restTemplate.getForObject(url, OpenLibrarySearchResDto.class);
        } catch (RestClientException e) {
            throw new CustomException(
                    "Error saat mengambil data dari Open Library: " + e.getMessage(),
                    HttpStatus.SERVICE_UNAVAILABLE.value()
            );
        }
    }
}