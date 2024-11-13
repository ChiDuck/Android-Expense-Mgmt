package com.example.quanlychitieu.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "category",
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id"))
public class Category implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private int id;
    @ColumnInfo(name = "category_name")
    private String name;
    @ColumnInfo(name = "category_type")
    private boolean type;
    @Nullable
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Category() {
    }

    public Category(String name, boolean type, int user_id) {
        this.name = name;
        this.type = type;
        this.user_id = user_id;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
