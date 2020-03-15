package com.example.libr.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idSession;
    private Long idUser;
    private LocalDateTime MyTime;

    public Session() {
    }

    public Session(Long idSession,Long idUser, LocalDateTime MyTime){
        this.idSession=idSession;
        this.idUser=idUser;
        this.MyTime=MyTime;
    }
    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getMyTime() {
        return MyTime;
    }

    public void setMyTime(LocalDateTime MyTime) {
        this.MyTime = MyTime;
    }
}
