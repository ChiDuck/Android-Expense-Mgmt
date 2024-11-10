package com.example.quanlychitieu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.adapter.UserAdapter;
import com.example.quanlychitieu.model.User;
import com.example.quanlychitieu.model.User;
import com.example.quanlychitieu.util.AppDatabase;
import com.example.quanlychitieu.viewmodel.UserViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText ten;
    Button luu;
    ListView list;
    ArrayList<User> ds;
    UserAdapter adapter;
    UserViewModel userVM;

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
//        ten = findViewById(R.id.txtten);
//        luu = findViewById(R.id.btnluu);
//        list = findViewById(R.id.list);
//        ds = new ArrayList<>();
//        adapter = new UserAdapter(MainActivity.this, R.layout.item_user,ds);
//        list.setAdapter(adapter);
//        userVM = new ViewModelProvider(this).get(UserViewModel.class);
//
//        userVM.deleteAll();
//
//        userVM.getAllUsers().observe(this, users -> {
//            if (users != null && !users.isEmpty()) {
//                adapter.setusers(users);
//                Toast.makeText(MainActivity.this,"Them thanh cong", Toast.LENGTH_LONG).show();
//            } else {
//                Log.d("MainActivity", "No u found");
//            }
//        });
//        luu.setOnClickListener(view -> {
//            String s = ten.getText().toString();
//            User u = new User();
//            u.setName(s);
//
//            userVM.insert(u);
//            ten.setText("");
//        });
    }
}