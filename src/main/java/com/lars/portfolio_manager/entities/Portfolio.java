package com.lars.portfolio_manager.entities;

import jakarta.persistence.*;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    private CustomUser owner;

    public Portfolio() {
    }

    public Portfolio(String name, CustomUser owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomUser getOwner() {
        return owner;
    }
}
