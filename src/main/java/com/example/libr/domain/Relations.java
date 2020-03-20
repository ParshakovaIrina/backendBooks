package com.example.libr.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Relations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRelation;
    private Long userid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Books books;

    public Relations() {
    }

   /* public Long getIdRelation() {
        return idRelation;
    }

    public void setIdRelation(Long idRelation) {
        this.idRelation = idRelation;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }*/
}
