package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.util.DateConverter;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    Activity context;
    int resource;
    List<Transaction> objects;
    TransactionViewModel tranVM;

    public TransactionAdapter(Activity context , int resource, List<Transaction> objects, TransactionViewModel tranVM) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.tranVM = tranVM;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtAmount = item.findViewById(R.id.txtAmount);
        TextView txtDate = item.findViewById(R.id.txtDate);
        TextView txtDes = item.findViewById(R.id.txtDescription);
        ImageButton ibtnDelete = item.findViewById(R.id.ibtnDelete);

        Transaction tran = this.objects.get(position);
        txtAmount.setText(tran.getAmount()+"");
        txtDate.setText(DateConverter.formatDate(tran.getDate()));
        txtDes.setText(tran.getDescription());

        ibtnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa");
            builder.setMessage("Bạn có chắc chắn mốn xóa giao dịch này?");
            builder.setPositiveButton("Có", (dialogInterface, i) -> tranVM.delete(tran));
            builder.setNegativeButton("Không", null);
            builder.show();
        });

        return item;
    }

    public void setTrans(List<Transaction> trans) {
        this.objects.clear();
        this.objects.addAll(trans);
        notifyDataSetChanged();
    }
}
