package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "USER")
public class User {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int id;
    @ColumnInfo(name = "username")
    private String name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password_hash")
    private String password;
    @ColumnInfo(name = "created_at")
    private Date created_at;
    @ColumnInfo(name = "last_login")
    private Date last_login;

    public User() {
    }

    public User(String name, String email, String password, Date created_at, Date last_login) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.last_login = last_login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }
}
