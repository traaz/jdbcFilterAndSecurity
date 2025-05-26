package com.example.jdbcFilterQuery.Models;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_information")
public class UserSecurity {
    private int id;
    private String name;
    private String password;

    public UserSecurity(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
