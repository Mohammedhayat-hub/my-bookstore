package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBook;
import com.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Integer> {
    boolean existsByBookAndUser(Book book, User user);
    // Finds books for a specific user
    List<MyBook> findByUser(User user);

    // Checks if a specific user already has a specific book
    boolean existsByTitleAndAuthorAndUser(String title, String author, User user);
}