package com.example.quanlychitieu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Query("SELECT * FROM `transaction` WHERE transaction_id= :id")
    Transaction getTransById(int id);
    @Query("SELECT * FROM `transaction`")
    List<Transaction> getALLTrans();
    @Insert
    void insertTrans(Transaction... transs);
    @Delete
    void deleteTrans(Transaction trans);
    @Update
    void updateTrans(Transaction... transs);
}
