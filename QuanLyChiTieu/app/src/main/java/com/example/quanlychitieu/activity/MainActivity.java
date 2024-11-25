package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
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

        BarChart barChart = findViewById(R.id.barChart);

        // Create entries for the chart
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, 10f)); // x = 1, y = 10
        barEntries.add(new BarEntry(2f, 20f)); // x = 2, y = 20
        barEntries.add(new BarEntry(3f, 30f)); // x = 3, y = 30
        barEntries.add(new BarEntry(4f, 40f)); // x = 4, y = 40

        // Create a dataset and give it a label
        BarDataSet barDataSet = new BarDataSet(barEntries, "Sales");
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // Create BarData with the dataset
        BarData barData = new BarData(barDataSet);

        // Set data to the chart
        barChart.setData(barData);

        // Customize the chart
        barChart.getDescription().setText("Monthly Sales Data");
        barChart.getDescription().setTextSize(12f);

        // Customize X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        // Refresh the chart
        barChart.invalidate(); // Refresh chart
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
            Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
            intent.putExtra("user_id",user_id);
            startActivity(intent);
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