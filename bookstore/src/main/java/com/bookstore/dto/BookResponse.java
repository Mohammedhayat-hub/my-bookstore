package com.bookstore.dto;

public class BookResponse {
    private Integer id;
    private String title;
    private String author;
    private int price;
    private String imageUrl;

    // Constructors
    public BookResponse() {}

    public BookResponse(Integer id, String title, String author, int price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters (Important for Spring to turn this into JSON)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}