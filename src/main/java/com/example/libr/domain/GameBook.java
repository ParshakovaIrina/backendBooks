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
public class GameBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    private String image;
    private String choiceOne;
    private Long linkOne;
    private String choiceTwo;
    private Long linkTwo;

    public GameBook() {
    }
}
