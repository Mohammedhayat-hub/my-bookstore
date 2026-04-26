package com.bookstore.controller;


import com.bookstore.entity.Book;
import com.bookstore.entity.MyBook;
import com.bookstore.service.BookService;

import com.bookstore.service.MyBookService;
import com.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MyBookService myBookService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/book_register")
    public String book_register(Model model){
        model.addAttribute("book", new Book());
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public String getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            java.security.Principal principal, // <--- ADD THIS
            Model model) {

        int pageSize = 5;
        var bookPage = bookService.getAllBooksPaginated(page, pageSize);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        // If logged in, get only THIS user's book titles
        if (principal != null) {
            com.bookstore.entity.User user = userService.findByUsername(principal.getName());
            model.addAttribute("myBookTitles", myBookService.getMyBookTitlesByUser(user));
        }

        return "bookList";
    }



    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return "redirect:/available_books";
    }

    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book b = bookService.getBookById(id);
        model.addAttribute("book", b);
        return "bookEdit";
    }

    @PostMapping("/save")
    public String addBook(@Valid @ModelAttribute("book") Book b, BindingResult result) {

        if (result.hasErrors()) {
            return "bookRegister";
        }

        bookService.save(b);
        return "redirect:/available_books";
    }


    @GetMapping("/addToMyBooks/{id}")
    public String addToMyBooks(@PathVariable int id, java.security.Principal principal, RedirectAttributes ra) {

        // 1. Get the actual book object from the store
        Book book = bookService.getBookById(id);

        // 2. Get the logged-in user
        String username = principal.getName();
        com.bookstore.entity.User user = userService.findByUsername(username);

        // 3. Save using the Service (We pass the objects, not the strings)
        // This is the method we added to MyBookService in the previous step
        boolean added = myBookService.saveMyBook(book, user);

        if (added) {
            ra.addFlashAttribute("msg", "Book added successfully!");
        } else {
            ra.addFlashAttribute("msg", "You already have this book in your collection!");
        }

        return "redirect:/available_books";
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model, java.security.Principal principal) {
        // 1. Get the current user
        String username = principal.getName();
        com.bookstore.entity.User user = userService.findByUsername(username);

        // 2. Fetch only books belonging to this user
        model.addAttribute("myBooks", myBookService.getBooksByUser(user));

        return "myBooks";
    }

    @GetMapping("/deleteMyBook/{id}")
    public String deleteMyBook(@PathVariable int id) {
        myBookService.deleteById(id);
        return "redirect:/my_books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {

        List<Book> list = bookService.searchBooks(keyword);

        model.addAttribute("books", list);
        model.addAttribute("myBookTitles", myBookService.getMyBookTitles());

        // ADD THESE 👇
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);

        if (list.isEmpty()) {
            model.addAttribute("msg", "No books found for: " + keyword);
        }

        return "bookList";
    }



    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable int id, Model model) {

        Book b = bookService.getBookById(id);

        if (b == null) {
            return "redirect:/available_books";
        }

        model.addAttribute("book", b);
        return "bookDetails";
    }
    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable int id, @RequestParam String status) {
        MyBook myBook = myBookService.getById(id);
        myBook.setStatus(status);
        myBookService.update(myBook);
        return "redirect:/my_books";
    }

}
