package com.example.libr.controllers;

import com.example.libr.domain.*;
import com.example.libr.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.management.Notification;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin()
@RestController
public class BooksController {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private RelationRepo relationsRepo;
    @Autowired
    private QueueRepo queueRepo;
    @Autowired
    private UserRepo userRepo;


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
            newMessage.setText("Новая книга " + bookDetail.getName());
            messageRepo.save(newMessage);
            return r;
        } else {
            return null;
        }
    }


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
            newMessage.setText("Книга " + book.getName() + " обновлена");
            messageRepo.save(newMessage);
            return updateBookDto;
        } else {
            return null;
        }
    }


    @CrossOrigin()
    @DeleteMapping("/books/{idUser}/{idBook}")
    public List<BooksDto> deleteRelation(@PathVariable Long idUser, @PathVariable Long idBook) {
        Relations relation = relationsRepo.findByUseridAndBooksId(idUser, idBook);
        relationsRepo.delete(relation);
        List<Relations> relations = relationsRepo.findAllByUserid(idUser);
        BookQueue nextUser = queueRepo.findFirstByBookid(idBook);
        if (nextUser != null) {
            MyUser user= userRepo.findById(nextUser.getUserid()).get();
            Relations newRelations = new Relations();
            newRelations.setUserid(nextUser.getUserid());
            newRelations.setBooks(booksRepo.findById(nextUser.getBookid()).get());
            queueRepo.delete(nextUser);
            relationsRepo.save(newRelations);
            sendSseEventsToUI(user);
        }
        List<BooksDto> userBooksDto = new ArrayList<>();
        for (Relations newRelation : relations) {
            Books userBook = newRelation.getBooks();
            BooksDto userBookDto = convert(userBook);
            userBooksDto.add(userBookDto);
        }
        return userBooksDto;
    }


    @CrossOrigin()
    @RequestMapping("/addInMyLibr/{idUser}")
    public Mess addInMyLibr(@PathVariable Long idUser, @RequestBody Long bookId) {
        BookQueue chekRelation = queueRepo.findByUseridAndBookid(idUser, bookId);
        Relations chekBuzyInRelarions = relationsRepo.findByUseridAndBooksId(idUser, bookId);
        Relations chekBuzy = relationsRepo.findByBooksId(bookId);
        Mess mess = new Mess();
        if (chekBuzyInRelarions == null) {
            if (chekBuzy == null) {
                Relations yu = new Relations();
                Books y = booksRepo.findById(bookId).get();
                yu.setUserid(idUser);
                yu.setBooks(y);
                relationsRepo.save(yu);
                mess.setMess("книга добавлена в пользование");

            } else {
                if (chekRelation == null) {
                    BookQueue newList = new BookQueue();
                    newList.setUserid(idUser);
                    newList.setBookid(bookId);
                    queueRepo.save(newList);
                    mess.setMess("книга добавлена в список ожидания");
                } else {
                    mess.setMess("книга уже в списке ожидания");
                }
            }
        } else {
            mess.setMess("книга находится у вас");
        }
        return mess;
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

    public static final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());


    public void sendSseEventsToUI(MyUser notification) { //your model class
        List<SseEmitter> sseEmitterListToRemove = new ArrayList<>();
        SseController.emitters.forEach((SseEmitter emitter) -> {
            try {
                emitter.send(notification, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                sseEmitterListToRemove.add(emitter);
                e.printStackTrace();
            }
        });
        SseController.emitters.removeAll(sseEmitterListToRemove);
    }
}
