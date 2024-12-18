package com.example.quanlychitieu.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlychitieu.dao.UserDAO;
import com.example.quanlychitieu.model.User;
import com.example.quanlychitieu.util.AppDatabase;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserDAO userDAO;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        allUsers = userDAO.getALLUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void insert(User user) {
        new Thread(() -> userDAO.insertUser(user)).start();
    }

    public void deleteAll() {
        new Thread(() -> userDAO.deleteAllUsers()).start();
    }

    public LiveData<User> signupCheck(String email) {
        return userDAO.signupCheck(email);
    }

    public LiveData<User> login(String email, String pass) {
        return userDAO.login(email,pass);
    }
}
