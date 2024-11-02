package com.example.quanlychitieu.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "budget",
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id"),
                       @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id")})
public class Budget {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "budget_id")
    private int id;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "start_date")
    private Date startdate;
    @ColumnInfo(name = "end_date")
    private Date enddate;
    @ColumnInfo(name = "category_id")
    private int category_id;
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Budget() {
    }

    public Budget(int amount, Date startdate, Date enddate, int category_id, int user_id) {
        this.amount = amount;
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
