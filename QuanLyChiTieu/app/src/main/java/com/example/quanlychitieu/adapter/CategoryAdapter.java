package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.User;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;
    List<Category> objects;

    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public View getView(int postion, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtName = item.findViewById(R.id.txtName);
        TextView txtType = item.findViewById(R.id.txtType);

        Category cat = this.objects.get(postion);
        txtName.setText(cat.getName());
        if (cat.isType()) txtType.setText("Thu nhập");
        else txtType.setText("Chi tiêu");

        return item;
    }

    public void setCats(List<Category> cats) {
        this.objects.clear();
        this.objects.addAll(cats);
        notifyDataSetChanged();
    }
}
