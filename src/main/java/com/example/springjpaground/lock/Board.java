package com.example.springjpaground.lock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Version
    private Long version;

    protected Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
