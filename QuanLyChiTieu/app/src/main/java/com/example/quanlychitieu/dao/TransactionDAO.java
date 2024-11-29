package com.example.quanlychitieu.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Query("SELECT * FROM `transaction` WHERE transaction_id = :id")
    Transaction getTranById(int id);
    @Query("SELECT * FROM `transaction` WHERE category_id = :id")
    LiveData<List<Transaction>> getALLTransByCat(int id);
    @Query("SELECT * FROM `transaction` WHERE user_id = :id")
    LiveData<List<Transaction>> getALLTransFromUser(int id);
    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getALLTrans();
    @Insert
    void insertTran(Transaction... trans);
    @Delete
    void deleteTran(Transaction tran);
    @Query("DELETE FROM 'transaction' WHERE category_id = :id")
    void deleteTransById(int id);
}
