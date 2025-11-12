package com.casa_training.casa_task_day_one.presentation.rest.controller;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.OpenLibrarySearchResDto;
import com.casa_training.casa_task_day_one.usecase.BookService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/search")
    @RateLimiter(name = "bookApi")
    public ResponseEntity<BaseResponse<OpenLibrarySearchResDto>> searchBooks(@RequestParam("query") String query) {
        OpenLibrarySearchResDto result = bookService.searchBooks(query);

        BaseResponse<OpenLibrarySearchResDto> response = new BaseResponse<>();
        response.setData(result);
        response.setMessage("Pencarian berhasil untuk query: " + query);
        return ResponseEntity.ok(response);
    }
}
