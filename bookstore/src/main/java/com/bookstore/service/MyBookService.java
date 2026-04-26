package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBook;
import com.bookstore.entity.User;
import com.bookstore.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyBookService {

    @Autowired
    private MyBookRepository myBookRepository;

    public boolean saveMyBook(Book book, User user) {
        // 1. Check if this user already has THIS specific book ID
        boolean exists = myBookRepository.existsByBookAndUser(book, user);

        if (!exists) {
            MyBook mb = new MyBook();
            mb.setUser(user);
            mb.setBook(book);
            myBookRepository.save(mb);
            return true;
        }
        return false;
    }

    public Iterable<MyBook> getAllMyBooks() {
        return myBookRepository.findAll();
    }

    public void deleteById(int id) {
        myBookRepository.deleteById(id);
    }

    public java.util.List<String> getMyBookTitles() {
        java.util.List<String> titles = new java.util.ArrayList<>();

        for (MyBook b : myBookRepository.findAll()) {
            titles.add(b.getTitle());
        }

        return titles;
    }

    // 1. Get ONLY the books for the logged-in user
    public java.util.List<MyBook> getBooksByUser(User user) {
        return myBookRepository.findByUser(user);
    }

    // 2. Get ONLY the titles for the logged-in user (for disabling buttons in the list)
    public java.util.List<String> getMyBookTitlesByUser(User user) {
        java.util.List<String> titles = new java.util.ArrayList<>();
        for (MyBook b : myBookRepository.findByUser(user)) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    public MyBook getById(int id) {
        return myBookRepository.findById(id)
                .orElseThrow(() -> new com.bookstore.exception.ResourceNotFoundException("Record not found with id: " + id));
    }

    public void update(MyBook myBook) {
        myBookRepository.save(myBook);
    }
}