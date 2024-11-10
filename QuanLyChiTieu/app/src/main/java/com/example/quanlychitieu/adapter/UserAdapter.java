package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    Activity context;
    int res;
    List<User> obj;

    public UserAdapter(Activity context ,int res, List<User> obj) {
        super(context, res, obj);
        this.context = context;
        this.res = res;
        this.obj = obj;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.res, null);
//        convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        TextView ten = item.findViewById(R.id.txtten);
        // Get the TextViews from the layout
        User user = this.obj.get(position);
        if (user != null) {
            ten.setText(user.getName());
        }

        return item;
    }

    // Optional: method to update the list of users
    public void setusers(List<User> users) {
        this.obj.clear();
        this.obj.addAll(users);
        notifyDataSetChanged();
    }
}
