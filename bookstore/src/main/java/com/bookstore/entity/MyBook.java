package com.bookstore.entity;

import jakarta.persistence.*;

@Entity
public class MyBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private String status = "Want to Read"; // Default value


    public MyBook() {}

    public MyBook(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    // GETTERS: These pull data directly from the linked Book object
    public String getTitle() {
        return (book != null) ? book.getTitle() : "No Title";
    }

    public String getAuthor() {
        return (book != null) ? book.getAuthor() : "No Author";
    }

    public int getPrice() {
        return (book != null) ? book.getPrice() : 0;
    }

    // Standard Getters and Setters for the IDs and Objects
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}