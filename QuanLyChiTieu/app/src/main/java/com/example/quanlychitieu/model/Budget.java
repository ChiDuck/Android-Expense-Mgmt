package com.example.quanlychitieu.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.quanlychitieu.util.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "budget",
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id"),
                       @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id")})
public class Budget implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "budget_id")
    private int id;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "balance")
    private int balance;
    @ColumnInfo(name = "start_date")
    @TypeConverters(DateConverter.class)
    private Date startdate;
    @ColumnInfo(name = "end_date")
    @TypeConverters(DateConverter.class)
    private Date enddate;
    @ColumnInfo(name = "category_id")
    private int category_id;
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Budget() {
    }

    public Budget(int amount, int balance, Date startdate, Date enddate, int category_id, int user_id) {
        this.amount = amount;
        this.balance = balance;
        this.startdate = startdate;
        this.enddate = enddate;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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
