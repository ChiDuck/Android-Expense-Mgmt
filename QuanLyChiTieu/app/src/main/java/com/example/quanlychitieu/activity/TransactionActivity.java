package com.example.quanlychitieu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.example.quanlychitieu.model.Budget;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.util.DateConverter;
import com.example.quanlychitieu.viewmodel.BudgetViewModel;
import com.example.quanlychitieu.viewmodel.NotificationViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    LinearLayout layout;
    TextView txtAmount, txtAmountLeft, txtFullDate;
    ImageButton ibtnBack;
    FloatingActionButton fabAddTran;
    ListView listTrans;
    ArrayList<Transaction> array;
    TransactionAdapter adapter;
    TransactionViewModel tranVM;
    BudgetViewModel budVM;
    NotificationViewModel notifVM;
    Calendar calendar;
    Budget budget;
    Category category;

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
        tranVM.getAllTransByCat(category.getId()).observe(this, trans -> {
            if (trans != null) {
                adapter.setTrans(trans);
            }
        });
        if (!category.isType()) {
            budVM.getBudgetById(category.getId()).observe(this, bud -> {
                if (bud != null) {
                    budget = bud;
                    txtAmount.setText(budget.getAmount() + " VND");
                    txtAmountLeft.setText(bud.getBalance() + " VND");
                    txtFullDate.setText(String.format("%s - %s", DateConverter.formatDate(budget.getStartdate()), DateConverter.formatDate(budget.getEnddate())));
                }
            });
        } else layout.setVisibility(View.GONE);
    }

    private void addEvents() {
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", 0);
        fabAddTran.setOnClickListener(view -> {
            showDialog(user_id, category.getId());
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
            DateConverter.dateProcessor(txtDate, calendar, this);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int amt = Integer.parseInt(txtAmount.getText().toString());
                Date date = calendar.getTime();
                String des = txtDes.getText().toString();
                Transaction tran = new Transaction(amt,date,des,cid,uid);
                try {
                    tranVM.insert(tran);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int newBalance = budget.getBalance() - amt;
                budVM.updateBalance(cid,budget.getBalance() - amt);
                if (newBalance < 0) {
                    String mes = "Số dư ngân sách trong danh mục " + category.getName() + " đã cạn kiệt!";
                    Notification notif = new Notification(mes, date, false, uid);
                    notifVM.insert(notif);
                } else
                if (newBalance <= budget.getBalance()/100 * 10 || newBalance < 5000)
                {
                    String mes = "Số dư ngân sách trong danh mục " + category.getName() + " gần cạn kiệt. Hãy cân nhắc chi tiêu trong tương lai.";
                    Notification notif = new Notification(mes, date, false, uid);
                    notifVM.insert(notif);
                }
            }
        });
        builder.setNegativeButton("Trở lại", null);
        builder.setCancelable(false);
        builder.show();
    }

    private void addControls() {
        layout = findViewById(R.id.linearLayout2);
        txtAmount = findViewById(R.id.txtAmount);
        txtAmountLeft = findViewById(R.id.txtAmountLeft);
        txtFullDate = findViewById(R.id.txtFullDate);
        ibtnBack = findViewById(R.id.ibtnBack);
        fabAddTran = findViewById(R.id.fabAddTran);
        listTrans = findViewById(R.id.listTrans);
        array = new ArrayList<>();
        tranVM = new ViewModelProvider(this).get(TransactionViewModel.class);
        budVM = new ViewModelProvider(this).get(BudgetViewModel.class); //initialize before adapter
        notifVM = new ViewModelProvider(this).get(NotificationViewModel.class); //initialize before adapter
        adapter = new TransactionAdapter(this,R.layout.trans_item,array,tranVM);
        listTrans.setAdapter(adapter);
        calendar = Calendar.getInstance();
        budget = new Budget();
        category = (Category) getIntent().getSerializableExtra("cat");
    }
}