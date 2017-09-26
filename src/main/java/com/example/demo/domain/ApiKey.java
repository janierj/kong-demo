package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "apikey")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {

        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {

        return id;
    }

    public String getKey() {
        return key;
    }
}
