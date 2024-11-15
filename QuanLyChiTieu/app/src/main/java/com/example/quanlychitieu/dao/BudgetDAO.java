package com.example.quanlychitieu.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Budget;

import java.util.List;

@Dao
public interface BudgetDAO {
    @Query("SELECT * FROM budget")
    LiveData<List<Budget>> getALLBudgets();

    @Insert
    void insertBudget(Budget... budgets);

    @Delete
    void deleteBudget(Budget budget);

    @Update
    void updateBudget(Budget... budgets);

    @Query("SELECT * FROM budget WHERE category_id = :id")
    LiveData<Budget> getBudgetByCategoryId(int id);

    @Query("UPDATE budget SET balance = :newbalance WHERE category_id = :id")
    void updateBalance(int id, int newbalance);

    @Query("DELETE FROM budget WHERE category_id = :id")
    void deleteById(int id);
}
