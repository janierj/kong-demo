package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Key;
import java.util.HashSet;

/**
 * Created by janier on 20/09/17.
 */
@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User owner;

    @NotNull(message = "Please, provide a name for the project.")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private ApiKey apiKey;

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public ApiKey getApiKey() {
        return apiKey;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
