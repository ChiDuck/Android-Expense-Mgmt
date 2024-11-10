package com.example.quanlychitieu.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Query("SELECT * FROM notification WHERE notification_id= :id")
    Notification getNotifById(int id);
    @Query("SELECT * FROM notification")
    LiveData<List<Notification>> getALLNotifs();
    @Insert
    void insertNotif(Notification... notifs);
    @Delete
    void deleteNotif(Notification notif);
    @Update
    void updateNotif(Notification... notifs);
}
