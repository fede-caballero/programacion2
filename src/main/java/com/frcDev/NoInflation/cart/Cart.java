package com.frcDev.NoInflation.cart;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<Product> product = new HashSet<>();

    @OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    public Cart() {
    }

    public Cart(Long id, Set<Product> product, User user) {
        this.id = id;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
