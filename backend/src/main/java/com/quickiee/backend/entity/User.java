
package com.quickiee.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class 
User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters & setters
    public Long getId() { return id; }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getPassword() { return password; }
}
