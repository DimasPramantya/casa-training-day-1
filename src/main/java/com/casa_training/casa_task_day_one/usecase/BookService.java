package com.casa_training.casa_task_day_one.usecase;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.OpenLibrarySearchResDto;
import com.casa_training.casa_task_day_one.presentation.rest.exception.CustomException;
import com.casa_training.casa_task_day_one.usecase.client.OpenLibraryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class BookService  {

    @Autowired
    private OpenLibraryClient openLibraryClient;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @CircuitBreaker(name = "thirdPartyApi", fallbackMethod = "searchBooksFallback")
    public OpenLibrarySearchResDto searchBooks(String query) {
        return openLibraryClient.fetchBooksByQuery(query);

    }

    public OpenLibrarySearchResDto searchBooksFallback(String query, Exception ex) {
        logger.warn("Circuit breaker fallback : {} | Error: {}", query, ex.getMessage());

        throw new CustomException(
                "Layanan pencarian buku tidak tersedia. Try again later",
                HttpStatus.SERVICE_UNAVAILABLE.value()
        );

    }
}