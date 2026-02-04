package com.quickiee.backend.testsupport;

public class TestUser {
    private final String name;
    private final String email;
    private final String password;
    private Long id;
    private String token;

    public TestUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Long getId() { return id; }
    public String getToken() { return token; }

    public void setId(Long id) { this.id = id; }
    public void setToken(String token) { this.token = token; }
}
