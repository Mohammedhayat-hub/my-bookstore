package com.bookstore.controller;

import com.bookstore.dto.BookResponse;
import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET all books - Returns 200 OK
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks().stream()
                .map(b -> new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getImageUrl()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(books);
    }

    // GET book by id - Uses our custom Exception if not found
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable int id) {
        Book b = bookService.getBookById(id);
        BookResponse response = new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getImageUrl());
        return ResponseEntity.ok(response);
    }

    // POST create new book - Returns 201 Created
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // DELETE book - Returns 204 No Content (Standard for Deletes)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}