package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class,parentColumns = "user_id", childColumns = "user_id"))
public class Notification {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    private int id;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "user_id")
    private int user_id;
}
