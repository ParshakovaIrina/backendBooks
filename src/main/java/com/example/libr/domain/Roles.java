package com.example.libr.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRole;
    private String role;
    private Boolean editBook;
    private Boolean deleteBook;
    private Boolean addBook;
    private Boolean admin;

    public Roles() {
    }

    public Roles(Long idRole, String role,
                 Boolean editBook, Boolean deleteBook, Boolean addBook, Boolean admin) {
        this.idRole = idRole;
        this.role = role;
        this.editBook = editBook;
        this.deleteBook = deleteBook;
        this.addBook = addBook;
        this.admin = admin;
    }

   /* public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEditBook() {
        return editBook;
    }

    public void setEditBook(Boolean editBook) {
        this.editBook = editBook;
    }

    public Boolean getDeleteBook() {
        return deleteBook;
    }

    public void setDeleteBook(Boolean deleteBook) {
        this.deleteBook = deleteBook;
    }

    public Boolean getAddBook() {
        return addBook;
    }

    public void setAddBook(Boolean addBook) {
        this.addBook = addBook;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }*/
}
