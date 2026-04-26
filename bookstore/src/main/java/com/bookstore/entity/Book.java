package com.bookstore.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;


@Entity
public class Book {



    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyBook> myBooks;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Min(1)
    private int price;
    private String imageUrl;
    private String genre;




    public Book (Integer id, String title, String author, int price) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.price =  price;

    }
    public Book() {
        super();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

}

