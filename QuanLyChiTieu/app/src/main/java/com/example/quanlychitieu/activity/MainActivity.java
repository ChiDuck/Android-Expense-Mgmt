package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.example.quanlychitieu.viewmodel.NotificationViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    TextView txtMoney;
    Button btnCat;
    ImageButton ibtnNotif;
    NotificationViewModel notifVM;
    List<Notification> notifications;
    TransactionViewModel tranVM;
    CategoryViewModel catVM;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
        notifVM.getNotifFromUser(user_id).observe(this, notifs -> {
            if (notifs != null ) {
                notifications = notifs;
            }
        });
        tranVM.getAllTrans().observe(this, trans -> {
            if (trans != null)
            {
                final int[] money = {0};
                for (Transaction t : trans){
                    catVM.getCatById(t.getCategory_id()).observe(this, cat -> {
                        if (cat != null) {
                            if (cat.isType()) money[0] += t.getAmount();
                            txtMoney.setText(money[0] + " VND");
                        }
                    });
                }
            }
        });
    }

    private void addEvents() {
        btnCat.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });

        ibtnNotif.setOnClickListener(view -> {
            for (Notification notif : notifications)
            {
                if (notif.getMessage() != null) {
                    int i = 0;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, notif.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }, i);
                    i += 2000;
                }
            }

        });
    }

    private void addControls() {
        btnCat = findViewById(R.id.btnCat);
        txtMoney = findViewById(R.id.txtMoney);
        ibtnNotif = findViewById(R.id.ibtnNotif);
        notifVM = new ViewModelProvider(this).get(NotificationViewModel.class); //initialize before adapter
        tranVM = new ViewModelProvider(this).get(TransactionViewModel.class); //initialize before adapter
        catVM = new ViewModelProvider(this).get(CategoryViewModel.class); //initialize before adapter
        user_id = getIntent().getIntExtra("user_id", 0);
    }
}