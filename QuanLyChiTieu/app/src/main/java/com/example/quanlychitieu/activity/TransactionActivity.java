package com.example.quanlychitieu.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.quanlychitieu.adapter.TransactionAdapter;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.util.DateConverter;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    ImageButton ibtnBack;
    FloatingActionButton fabAddTran;
    ListView listTrans;
    ArrayList<Transaction> array;
    TransactionAdapter adapter;
    TransactionViewModel tranVM;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
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
        int cat_id = getIntent().getIntExtra("cat_id", 0);
        tranVM.getAllTransByCat(cat_id).observe(this, trans -> {
            if (trans != null) {
                adapter.setTrans(trans);
            }
        });
    }

    private void addEvents() {
        Intent preIntent = getIntent();
        int user_id = preIntent.getIntExtra("user_id", 0);
        int cat_id = preIntent.getIntExtra("cat_id", 0);
        fabAddTran.setOnClickListener(view -> {
            showDialog(user_id, cat_id);
        });

        ibtnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void showDialog(int uid, int cid) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.trans_add_dialog,null);

        EditText txtAmount = view.findViewById(R.id.txtAmount);
        TextView txtDate = view.findViewById(R.id.txtDate);
        ImageButton ibtnDate = view.findViewById(R.id.ibtnDate);
        EditText txtDes = view.findViewById(R.id.txtDescription);

        ibtnDate.setOnClickListener(view1 -> {
            dateProcessor(txtDate);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Thêm giao dịch");
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int amt = Integer.parseInt(txtAmount.getText().toString());
                Date date = calendar.getTime();
                String des = txtDes.getText().toString();
                Transaction tran = new Transaction(amt,date,des,cid,uid);
                tranVM.insert(tran);
            }
        });
        builder.setNegativeButton("Trở lại", null);
        builder.setCancelable(false);
        builder.show();
    }

    private void dateProcessor(TextView date) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                date.setText(DateConverter.formatDate(calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }

    private void addControls() {
        ibtnBack = findViewById(R.id.ibtnBack);
        fabAddTran = findViewById(R.id.fabAddTran);
        listTrans = findViewById(R.id.listTrans);
        array = new ArrayList<>();
        tranVM = new ViewModelProvider(this).get(TransactionViewModel.class);
        adapter = new TransactionAdapter(this,R.layout.trans_item,array,tranVM);
        listTrans.setAdapter(adapter);
        calendar = Calendar.getInstance();
    }
}