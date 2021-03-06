package com.example.libr.domain;

import javax.persistence.*;
import javax.swing.text.JTextComponent;
import javax.xml.soap.Text;
import java.awt.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.sql.NVarcharTypeDescriptor;

@Getter
@Setter
@Entity
public class Books {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String author;
    private String year;
    private String genre;
    private String description;
    private String image;
    @OneToMany(mappedBy = "books",  fetch = FetchType.EAGER)
    Set<Relations> relations;
    public Books() {
    }
  /*  public Books(String name, String author){
            this.name=name;
            this.author=author;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/
}
