package com.example.quanlychitieu.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.quanlychitieu.util.DateConverter;

import java.util.Date;

@Entity(tableName ="notification",
        foreignKeys = @ForeignKey(entity = User.class,parentColumns = "user_id", childColumns = "user_id"))
public class Notification {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    private int id;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    private Date date;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Notification() {
    }

    public Notification(String message, Date date, String status, int user_id) {
        this.message = message;
        this.date = date;
        this.status = status;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
