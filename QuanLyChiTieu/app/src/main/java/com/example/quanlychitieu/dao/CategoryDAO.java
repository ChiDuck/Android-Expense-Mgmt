package com.example.quanlychitieu.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category WHERE category_id= :id")
    Category getCatById(int id);
    @Query("SELECT * FROM category")
    LiveData<List<Category>> getALLCats();
    @Insert
    void insertCat(Category... cats);
    @Delete
    void deleteCat(Category cat);
    @Update
    void updateCat(Category... cats);
}
