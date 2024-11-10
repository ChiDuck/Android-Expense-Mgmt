package com.example.quanlychitieu.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.quanlychitieu.util.DateConverter;

import java.util.Date;

@Entity(tableName = "transaction",
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id"),
                       @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id")})
public class Transaction {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    private int id;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    private Date date;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "category_id")
    private int category_id;
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Transaction() {
    }

    public Transaction(int amount, Date date, String description, int category_id, int user_id) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category_id = category_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
