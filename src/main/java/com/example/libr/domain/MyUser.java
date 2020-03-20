package com.example.libr.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usr")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String role;
//    @OneToMany(mappedBy = "user",  fetch = FetchType.EAGER)
//    Set<Relations> relationsUser;

    public MyUser() {
    }


  /*  public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Set<Relations> getRelationes() {
        return relationes;
    }

    public void setRelationes(Set<Relations> relationes) {
        this.relationes = relationes;
    }*/
}
