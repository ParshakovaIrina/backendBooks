package com.example.libr.controllers;

import com.example.libr.domain.*;
import com.example.libr.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@RestController
public class MyController {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private RelationRepo relationsRepo;

//    @CrossOrigin()
//    @PostMapping("/registration")
//    public boolean addUser(@RequestBody MyUser user) {
//        MyUser addUser = userRepo.findByLogin(user.getLogin());
//        if (addUser == null) {
//            userRepo.save(user);
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @CrossOrigin()
//    @PostMapping("/login")
//    public MyUser loginUser(@RequestBody MyUser user) {
//        MyUser tyu = userRepo.findByLoginAndPassword(user.getLogin(), user.getPassword());
//        if (tyu == null) {
//            return null;
//        } else {
//            Session mySess = sessionRepo.findByIdUser(tyu.getId());
//            if (mySess == null) {
//                Session session = new Session();
//                session.setIdUser(tyu.getId());
//                session.setMyTime(LocalDateTime.now().plusMinutes(2));
//                sessionRepo.save(session);
//                return tyu;
//            } else {
//                mySess.setMyTime(LocalDateTime.now().plusMinutes(2));
//                sessionRepo.save(mySess);
//                return tyu;
//            }
//        }
//
//    }

    public static void main(String[] args) {
        SpringApplication.run(MyController.class, args);
    }


    @CrossOrigin()
    @GetMapping("/detail/{idUser}/{idBook}")
    public BooksDto getBook(@PathVariable Long idUser, @PathVariable Long idBook) {
        if (checkSession(idUser)) {
            Books book = booksRepo.findById(idBook).get();
            BooksDto userBook = convert(book);
            return userBook;
        } else {
            return null;
        }
    }

//    @CrossOrigin()
//    @GetMapping("/roles/{idUser}")
//    public Roles getRole(@PathVariable Long idUser) {
//        MyUser my = userRepo.findById(idUser).get();
//        Roles myRole = rolesRepo.findByRole(my.getRole());
//        return myRole;
//    }

    @CrossOrigin()
    @GetMapping("/books/{idUser}")
    public List<BooksDto> getBooks(@PathVariable Long idUser) {
        if (checkSession(idUser)) {
            List<Books> books = booksRepo.findAll();
            List<BooksDto> r = new ArrayList<>();
            for (Books t : books) {
                BooksDto uio = convert(t);
                r.add(uio);
            }
            return r;
            // List <BooksDto> uio= convert(books);
        } else {
            return null;
        }
    }


    @CrossOrigin()
    @PostMapping("books/{idUser}")
    public List<BooksDto> add(@PathVariable Long idUser, @RequestBody Books bookDetail) {
        if (checkSession(idUser)) {
            booksRepo.save(bookDetail);
            List<Books> books = booksRepo.findAll();
            List<BooksDto> r = new ArrayList<>();
            for (Books t : books) {
                BooksDto uio = convert(t);
                r.add(uio);
            }
            Message newMessage = new Message();
            newMessage.setLogin("admin");
            newMessage.setText("Новая книга "+bookDetail.getName());
            messageRepo.save(newMessage);
            return r;
        } else {
            return null;
        }
    }

    @CrossOrigin()
    @PostMapping("message/{idUser}")
    public List<Message> addMessage(@PathVariable Long idUser, @RequestBody String message) {
        if (checkSession(idUser)) {
            Message newMessage = new Message();
            MyUser user = (userRepo.findById(idUser).get());
            newMessage.setLogin(user.getLogin());
            newMessage.setText(message);
            messageRepo.save(newMessage);
            List<Message> messages = messageRepo.findAll();
            return messages;
        } else {
            return null;
        }
    }
    @CrossOrigin()
    @GetMapping("message/{idUser}")
    public List<Message> showMessage(@PathVariable Long idUser) {
        if (checkSession(idUser)) {
            List<Message> messages = messageRepo.findAll();
            return messages;
        } else {
            return null;
        }
    }

//    @CrossOrigin()
//    @GetMapping("/admin-page")
//    public List<MyUser> getUsers() {
//        List<MyUser> users = userRepo.findAll();
//        return users;
//    }

    @CrossOrigin()
    @DeleteMapping("/detail/{id}")
    public List<BooksDto> deleteBook(@PathVariable Long id) {
        List<BooksDto> booksDto = new ArrayList<>();
        Books myBook = booksRepo.findById(id).get();
        List<Relations> booksRelations = relationsRepo.findAllByBooksId(id);
        for (Relations bookRelations : booksRelations) {
            relationsRepo.delete(bookRelations);
        }
        booksRepo.delete(myBook);
        List<Books> books = booksRepo.findAll();
        for (Books book : books) {
            BooksDto bookDto = convert(book);
            booksDto.add(bookDto);
        }
        return booksDto;
    }

    @CrossOrigin()
    @PutMapping("/detail/{idUser}/{idBook}")
    public BooksDto updateBook(@PathVariable Long idUser, @PathVariable(value = "idBook") Long bookId,
                               @Valid @RequestBody Books bookDetail) {
        if (checkSession(idUser)) {
            Books book = booksRepo.findById(bookId).get();
            book.setName(bookDetail.getName());
            book.setAuthor(bookDetail.getAuthor());
            book.setDescription(bookDetail.getDescription());
            book.setGenre(bookDetail.getGenre());
            book.setGenre(bookDetail.getGenre());
            book.setYear(bookDetail.getYear());
            final Books updateBook = booksRepo.save(book);
            final BooksDto updateBookDto = convert(updateBook);
            Message newMessage = new Message();
            newMessage.setLogin("admin");
            newMessage.setText("Книга "+book.getName()+" обновлена");
            messageRepo.save(newMessage);
            return updateBookDto;
        } else {
            return null;
        }
    }


//    @CrossOrigin()
//    @DeleteMapping("admin-page/{id}")
//    public List<MyUser> deleteUser(@PathVariable Long id) {
//        MyUser user = userRepo.findById(id).get();
//        userRepo.delete(user);
//        List<MyUser> users = userRepo.findAll();
//        return users;
//    }

    @CrossOrigin()
    @DeleteMapping("/books/{idUser}")
    public void deleteSession(@PathVariable Long idUser) {
        Session session = sessionRepo.findByIdUser(idUser);
        sessionRepo.delete(session);
    }

    @CrossOrigin()
    @DeleteMapping("/books/{idUser}/{idBook}")
    public List<BooksDto> deleteRelation(@PathVariable Long idUser, @PathVariable Long idBook) {
        Relations relation = relationsRepo.findByUseridAndBooksId(idUser, idBook);
        relationsRepo.delete(relation);
        List<Relations> relations = relationsRepo.findAllByUserid(idUser);
        List<BooksDto> userBooksDto = new ArrayList<>();
        for (Relations newRelation : relations) {
            Books userBook = newRelation.getBooks();
            BooksDto userBookDto = convert(userBook);
            userBooksDto.add(userBookDto);
        }
        return userBooksDto;
    }

//    @CrossOrigin()
//    @PutMapping("admin-page/{id}")
//    public List<MyUser> updateUser(@PathVariable Long id,
//                                   @Valid @RequestBody MyUser userDetail) {
//        MyUser user = userRepo.findById(id).get();
//        user.setLogin(userDetail.getLogin());
//        user.setPassword(userDetail.getPassword());
//        user.setRole(userDetail.getRole());
//        userRepo.save(user);
//        List<MyUser> users = userRepo.findAll();
//        return users;
//    }


    @CrossOrigin()
    @PostMapping("/addInMyLibr/{idUser}")
    public boolean addInMyLibr(@PathVariable Long idUser, @RequestBody Long bookId) {
        Relations chekRelation = relationsRepo.findByUseridAndBooksId(idUser, bookId);
        if (chekRelation == null) {
            Relations yu = new Relations();
            Books y = booksRepo.findById(bookId).get();
            yu.setUserid(idUser);
            yu.setBooks(y);
            relationsRepo.save(yu);
        }
        return true;
    }

    public Boolean checkSession(Long idUser) {
        Session mySession = sessionRepo.findByIdUser(idUser);
        if (mySession == null) {
            return false;
        } else {
            LocalDateTime MyData = mySession.getMyTime();
            LocalDateTime NowData = LocalDateTime.now();
            if (MyData.compareTo(NowData) > 0) {
                return true;
            } else {
                sessionRepo.delete(mySession);
                return false;
            }
        }
    }

    @CrossOrigin()
    @GetMapping("/userLibrary/{idUser}")
    public List<BooksDto> getUserBooks(@PathVariable Long idUser) {
        List<Relations> relations = relationsRepo.findAllByUserid(idUser);
        List<BooksDto> userBooksDto = new ArrayList<>();
        for (Relations relation : relations) {
            Books userBook = relation.getBooks();
            BooksDto userBookDto = convert(userBook);
            userBooksDto.add(userBookDto);
        }
        return userBooksDto;
    }

    public BooksDto convert(Books book) {
        BooksDto bookDto = new BooksDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setName(book.getName());
        bookDto.setYear(book.getYear());
        bookDto.setGenre(book.getGenre());
        bookDto.setDescription(book.getDescription());
        bookDto.setImage(book.getImage());
        return bookDto;
    }


}
