package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.viewmodel.NotificationViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    Activity context;
    int resource;
    List<Notification> objects;
    NotificationViewModel notifVM;

    public NotificationAdapter(Activity context , int resource, List<Notification> objects, NotificationViewModel notifVM) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.notifVM = notifVM;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtMes = item.findViewById(R.id.txtMessage);
        TextView txtDate = item.findViewById(R.id.txtDate);
        ImageButton ibtnDelete = item.findViewById(R.id.ibtnDelete);
        LinearLayout item_bg = item.findViewById(R.id.notif_item_bg);

        Notification notif = this.objects.get(position);

        txtMes.setText(notif.getMessage());
        txtDate.setText(notif.getMessage());
        if (!notif.isStatus()) item_bg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CBCBCB")));

        ibtnDelete.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Xóa");
//            builder.setMessage("Bạn có chắc chắn mốn xóa giao dịch này?");
//            builder.setPositiveButton("Có", (dialogInterface, i) -> tranVM.delete(tran));
//            builder.setNegativeButton("Không", null);
//            builder.show();
            notifVM.delete(notif);
        });

        item.setOnClickListener(view -> {
            notif.setStatus(true);
            notifVM.update(notif);
            item_bg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EAEAEA")));
        });

        return item;
    }

    public void setNotifs(List<Notification> notifs) {
        this.objects.clear();
        this.objects.addAll(notifs);
        Collections.reverse(this.objects);
        notifyDataSetChanged();
    }
}
