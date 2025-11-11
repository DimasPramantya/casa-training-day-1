package com.casa_training.casa_task_day_one.usecase;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.OpenLibrarySearchResDto;
import com.casa_training.casa_task_day_one.usecase.client.OpenLibraryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService  {

    @Autowired
    private OpenLibraryClient openLibraryClient;


    public OpenLibrarySearchResDto searchBooks(String query) {
        //TODO add handling exception
        return openLibraryClient.fetchBooksByQuery(query);

    }
}