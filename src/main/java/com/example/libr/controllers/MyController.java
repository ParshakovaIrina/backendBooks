package com.example.libr.controllers;

import com.example.libr.domain.Books;
import com.example.libr.domain.Message;
import com.example.libr.domain.MyUser;
import com.example.libr.repos.BooksRepo;
import com.example.libr.repos.MessageRepo;
import com.example.libr.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin()
@RestController
public class MyController {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private UserRepo userRepo;

    @CrossOrigin()
    @PostMapping("/registration")
    public boolean addUser(@RequestBody MyUser user) {
        MyUser addUser = userRepo.findByLogin(user.getLogin());
        if (addUser==null) {
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @CrossOrigin()
    @PostMapping("/login")
    public MyUser loginUser(@RequestBody MyUser user) {
       MyUser tyu = userRepo.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (tyu==null){
            return null;
        }else{
            return tyu;
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(MyController.class, args);
    }

    @GetMapping("/")
    public String greeting()
    //   @RequestParam(name = "name", required = false, defaultValue = "World") String name,
    //  Map<String, Object> model
    {
        /*    model.put("name", name);*/
        return "greeting";
    }

    @GetMapping("/detail/{id}")
    public Optional<Books> getBook(@PathVariable Long id) {
        Optional<Books> book = booksRepo.findById(id);
        return book;
    }

    @CrossOrigin()
    @GetMapping("/books")
    public List<Books> getBooks() {
        List<Books> books = booksRepo.findAll();
        return books;
    }

    @CrossOrigin()
    @GetMapping("/admin-page")
    public List<MyUser> getUsers() {
        List<MyUser> users = userRepo.findAll();
        return users;
    }

    @CrossOrigin()
    @DeleteMapping("/detail/{id}")
    public List<Books> deleteBook(@PathVariable Long id) {
        Books book = booksRepo.findById(id).get();
        booksRepo.delete(book);
        List<Books> booki = booksRepo.findAll();
        return booki;
    }

    @CrossOrigin()
    @PutMapping("/detail/{id}")
    public Books updateBook(@PathVariable(value = "id") Long bookId,
                            @Valid @RequestBody Books bookDetail) {
        Books book = booksRepo.findById(bookId).get();
        book.setName(bookDetail.getName());
        book.setAuthor(bookDetail.getAuthor());
        book.setDescription(bookDetail.getDescription());
        book.setGenre(bookDetail.getGenre());
        book.setGenre(bookDetail.getGenre());
        book.setYear(bookDetail.getYear());
        final Books updateBook = booksRepo.save(book);
        return updateBook;
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {

        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        Iterable<Books> books = booksRepo.findAll();
        model.put("books", books);
        return "main";
    }

    @PostMapping("main")
    public String main(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @CrossOrigin()
    @PostMapping("books")
    public Books add(@RequestBody Books bookDetail) {
        Books NewBook = booksRepo.save(bookDetail);
        return NewBook;
    }

    @CrossOrigin()
    @DeleteMapping("admin-page/{id}")
    public List<MyUser> deleteUser(@PathVariable Long id) {
        MyUser user = userRepo.findById(id).get();
        userRepo.delete(user);
        List<MyUser> users = userRepo.findAll();
        return users;
    }

    @CrossOrigin()
    @PutMapping("admin-page/{id}")
    public List<MyUser> updateUser(@PathVariable Long id,
                                   @Valid @RequestBody MyUser userDetail) {
        MyUser user = userRepo.findById(id).get();
        user.setLogin(userDetail.getLogin());
        user.setPassword(userDetail.getPassword());
        user.setRole(userDetail.getRole());
        userRepo.save(user);
        List<MyUser> users = userRepo.findAll();
        return users;
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if ((filter != null) && !(filter.isEmpty())) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "main";

    }
}
