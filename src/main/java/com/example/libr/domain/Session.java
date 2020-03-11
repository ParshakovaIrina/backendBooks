package com.example.libr.domain;

import java.sql.Time;

public class Session {
    private Long id;
    private Time myTime;

    public Session() { };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getMyTime() {
        return myTime;
    }

    public void setMyTime(Time myTime) {
        this.myTime = myTime;
    }
}