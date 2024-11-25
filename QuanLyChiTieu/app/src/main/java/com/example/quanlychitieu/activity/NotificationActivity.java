package com.example.quanlychitieu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.adapter.NotificationAdapter;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.viewmodel.NotificationViewModel;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ImageButton ibtnBack;
    ListView listNotifs;
    TextView txtNoNotif;
    ArrayList<Notification> array;
    NotificationAdapter adapter;
    NotificationViewModel notifVM;
    int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        dataObserve();
        addEvents();
    }

    private void dataObserve() {
        notifVM.getAllNotifs().observe(this, notifs -> {
            if (notifs != null) {
                txtNoNotif.setVisibility(View.GONE);
                adapter.setNotifs(notifs);
            } else txtNoNotif.setVisibility(View.VISIBLE);
        });
    }

    private void addEvents() {
        ibtnBack.setOnClickListener(view -> finish());
    }

    private void addControls() {
        ibtnBack = findViewById(R.id.ibtnBack);
        listNotifs = findViewById(R.id.listNotifs);
        txtNoNotif = findViewById(R.id.txtNoNotif);
        array = new ArrayList<>();
        notifVM = new ViewModelProvider(this).get(NotificationViewModel.class);
        adapter = new NotificationAdapter(this,R.layout.notifs_item,array,notifVM);
        listNotifs.setAdapter(adapter);
        user_id = getIntent().getIntExtra("user_id", 0);
    }
}