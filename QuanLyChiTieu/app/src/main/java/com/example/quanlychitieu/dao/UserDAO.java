package com.example.quanlychitieu.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user WHERE user_id = :id")
    User getUserById(int id);
    @Query("SELECT * FROM user")
    LiveData<List<User>> getALLUsers();
    @Insert
    void insertUser(User... users);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User... users);
    @Query("DELETE FROM user")
    void deleteAllUsers();
}
