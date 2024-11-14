package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.activity.CategoryDetailActivity;
import com.example.quanlychitieu.activity.TransactionActivity;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;
    List<Category> objects;
    CategoryViewModel catVM;
    TransactionViewModel tranVM;

    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects,
                           CategoryViewModel catVM, TransactionViewModel tranVM) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.catVM = catVM;
        this.tranVM = tranVM;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtName = item.findViewById(R.id.txtName);
        TextView txtType = item.findViewById(R.id.txtType);
        ImageButton ibtnDelete = item.findViewById(R.id.ibtnDelete);
        ImageButton ibtnEdit = item.findViewById(R.id.ibtnEdit);

        Category cat = this.objects.get(position);
        txtName.setText(cat.getName());
        if (cat.isType()) txtType.setText("Thu nhập");
        else txtType.setText("Chi tiêu");

        ibtnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa danh mục này? Tất cả giao dịch trong danh mục sẽ đồng thời bị xóa.");
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                try {
                    tranVM.deleteTransById(cat.getId());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                catVM.delete(cat);
            });
            builder.setNegativeButton("Không", null);
            builder.show();
        });
        ibtnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoryDetailActivity.class);
            intent.putExtra("oldcat", cat);
            context.startActivityForResult(intent,3);
        });

        item.setOnClickListener(view -> {
            Intent intent = new Intent(context, TransactionActivity.class);
            int user_id = context.getIntent().getIntExtra("user_id", 0);
            intent.putExtra("cat_id",cat.getId());
            intent.putExtra("user_id",user_id);
            context.startActivity(intent);
        });

        return item;
    }

    public void setCats(List<Category> cats) {
        this.objects.clear();
        this.objects.addAll(cats);
        notifyDataSetChanged();
    }
}
