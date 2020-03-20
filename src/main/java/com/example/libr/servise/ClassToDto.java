package com.example.libr.servise;


import com.example.libr.domain.Books;
import com.example.libr.domain.BooksDto;

import java.util.ArrayList;
import java.util.List;

public class ClassToDto {
    public BooksDto convert(Books book) {
        BooksDto bookDto = new BooksDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setName(book.getName());
        bookDto.setYear(book.getYear());
        bookDto.setGenre(book.getGenre());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }
}
