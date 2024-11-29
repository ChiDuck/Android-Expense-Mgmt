package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.example.quanlychitieu.viewmodel.NotificationViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtMoney;
    Button btnCat, btnMonth, bthWeek, btnDay;
    ImageButton ibtnNotif;
    ImageView iconNotif;
    BarChart barChart;
    NotificationViewModel notifVM;
    TransactionViewModel tranVM;
    CategoryViewModel catVM;
    Observer<Category> observer;
    List<LiveData<Category>> listLiveCat;
    int user_id, totalMoney;
    float monthlyIn, monthlyEx,
        weeklyIn, weeklyEx,
        dailyIn, dailyEx;
    boolean catType = true;

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

    private void addBarChart(float in, float ex) {

        // Create entries for the chart
        ArrayList<BarEntry> barEntriesIn = new ArrayList<>();
        barEntriesIn.add(new BarEntry(1f, in)); // x = 2, y = 20
        ArrayList<BarEntry> barEntriesEx = new ArrayList<>();
        barEntriesEx.add(new BarEntry(2f, ex)); // x = 3, y = 30

        // Create a dataset and give it a label
        BarDataSet barDataSetIn = new BarDataSet(barEntriesIn, "Thu nhập");
        barDataSetIn.setColor(Color.parseColor("#8ACD4F"));
        barDataSetIn.setValueTextColor(Color.BLACK);
        barDataSetIn.setValueTextSize(16f);

        BarDataSet barDataSetEx = new BarDataSet(barEntriesEx, "Chi tiêu");
        barDataSetEx.setColor(Color.parseColor("#EA6464"));
        barDataSetEx.setValueTextColor(Color.BLACK);
        barDataSetEx.setValueTextSize(16f);

        // Create BarData with the dataset
        BarData barData = new BarData(barDataSetIn, barDataSetEx);
        barData.setBarWidth(0.6f);
        // Set data to the chart
        barChart.setData(barData);

        // Customize X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(1f);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15f);

        barChart.getAxisLeft().setTextSize(12f);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setTextSize(16f);
        barChart.getLegend().setXEntrySpace(20f);

        barChart.animateXY(1500, 1500, Easing.EaseInOutQuad);
        // Refresh the chart
        barChart.invalidate(); // Refresh chart
    }

    private void dataObserve() {
        notifVM.getNotifsFromUser(user_id).observe(this, notifs -> {
            if (notifs != null ) {
                for (Notification n: notifs){
                    if (!n.isStatus()) {
                        iconNotif.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                iconNotif.setVisibility(View.GONE);
            }
        });

        tranVM.getAllTransFromUser(user_id).observe(this, trans -> {
            if (trans != null) {
                totalMoney = 0;
                monthlyIn = 0; monthlyEx = 0;
                weeklyIn = 0; weeklyEx = 0;
                dailyIn = 0; dailyEx = 0;
                for (int i = 0; i < trans.size(); i++) {
                    Transaction t = trans.get(i);
                    observer = cat -> {
                        if (cat != null) {
                            if (cat.isType()) totalMoney += t.getAmount();
                            else totalMoney -= t.getAmount();
                            txtMoney.setText(totalMoney + " VND");
                            catType = cat.isType();
                            calculateInEx(t);
                            addBarChart(monthlyIn,monthlyEx);
                        }
                    };

                    // Ensure we don't re-register observers for the same category
                    if (!catVM.getCatById(t.getCategory_id()).hasObservers()) {
                        catVM.getCatById(t.getCategory_id()).observe(this, observer);
                    }
                    listLiveCat.add(catVM.getCatById(t.getCategory_id()));
                }
            }
        });
    }

    private void calculateInEx(Transaction t) {
        // Calculate total income and outcome in this month/week/day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(t.getDate());
        int month = calendar.get(Calendar.MONTH);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        int day = calendar.get(Calendar.DATE);

        if (month == Calendar.getInstance().get(Calendar.MONTH)) {
            if (catType) monthlyIn += t.getAmount();
            else monthlyEx += t.getAmount();

            if (week == Calendar.getInstance().get(Calendar.WEEK_OF_MONTH)) {
                if (catType) weeklyIn += t.getAmount();
                else weeklyEx += t.getAmount();
            }
            if (day == Calendar.getInstance().get(Calendar.DATE)) {
                if (catType) dailyIn += t.getAmount();
                else dailyEx += t.getAmount();
            }
        }
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

        btnMonth.setOnClickListener(view -> addBarChart(monthlyIn,monthlyEx));
        bthWeek.setOnClickListener(view -> addBarChart(weeklyIn,weeklyEx));
        btnDay.setOnClickListener(view -> addBarChart(dailyIn,dailyEx));
    }

    private void addControls() {
        btnCat = findViewById(R.id.btnCat);
        btnMonth = findViewById(R.id.btnMonth);
        bthWeek = findViewById(R.id.btnWeek);
        btnDay = findViewById(R.id.btnDay);
        txtMoney = findViewById(R.id.txtMoney);
        ibtnNotif = findViewById(R.id.ibtnNotif);
        iconNotif = findViewById(R.id.iconNotif);
        barChart = findViewById(R.id.barChart);
        listLiveCat = new ArrayList<>();
        notifVM = new ViewModelProvider(this).get(NotificationViewModel.class); //initialize before adapter
        catVM = new ViewModelProvider(this).get(CategoryViewModel.class);
        tranVM = new ViewModelProvider(this).get(TransactionViewModel.class);
        user_id = getIntent().getIntExtra("user_id", 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        totalMoney = 0;
        monthlyIn = 0; monthlyEx = 0;
        weeklyIn = 0; weeklyEx = 0;
        dailyIn = 0; dailyEx = 0;
    }
}