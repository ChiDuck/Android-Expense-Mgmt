package com.example.quanlychitieu.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlychitieu.dao.NotificationDAO;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.util.AppDatabase;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private final NotificationDAO notifDAO;
    private final LiveData<List<Notification>> allNotifs;
    
    public NotificationViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        notifDAO = db.notifDAO();
        allNotifs = notifDAO.getALLNotifs();
    }

    public LiveData<List<Notification>> getAllNotifs() {
        return allNotifs;
    }

    public void insert(Notification notif) {
        new Thread(() -> notifDAO.insertNotif(notif)).start();
    }

    public void delete(Notification notif) {
        new Thread(() -> notifDAO.deleteNotif(notif)).start();
    }

    public LiveData<List<Notification>> getNotifFromUser(int id) {
        return notifDAO.getNotifFromUser(id);
    }
}
