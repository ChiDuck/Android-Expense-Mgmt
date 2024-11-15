package com.example.quanlychitieu.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.activity.CategoryDetailActivity;
import com.example.quanlychitieu.activity.TransactionActivity;
import com.example.quanlychitieu.model.Budget;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.util.DateConverter;
import com.example.quanlychitieu.viewmodel.BudgetViewModel;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;
    List<Category> objects;
    CategoryViewModel catVM;
    TransactionViewModel tranVM;
    BudgetViewModel budVM;

    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects,
                           CategoryViewModel catVM, TransactionViewModel tranVM, BudgetViewModel budVM) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.catVM = catVM;
        this.tranVM = tranVM;
        this.budVM = budVM;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtName = item.findViewById(R.id.txtName);
        TextView txtType = item.findViewById(R.id.txtType);
        ImageButton ibtnDelete = item.findViewById(R.id.ibtnDelete);
        ImageButton ibtnEdit = item.findViewById(R.id.ibtnEdit);
        ImageButton ibtnBudget = item.findViewById(R.id.ibtnBudget);
        TextView txtBudget = item.findViewById(R.id.txtBudget);
        LinearLayout layoutBudget = item.findViewById(R.id.layoutBudget);

        Category cat = this.objects.get(position);

        budVM.getBudgetById(cat.getId()).observe((LifecycleOwner) context, bud -> {
            if (bud != null)
            {
                txtBudget.setText(bud.getAmount()+" VND");
                Log.i("budget", bud.getAmount()+" VND");
            }
        });
        txtName.setText(cat.getName());
        if (cat.isType())
        {
            txtType.setText("Thu nhập");
            ibtnBudget.setVisibility(View.GONE);
            layoutBudget.setVisibility(View.GONE);
        }
        else {
            txtType.setText("Chi tiêu");
        }

        ibtnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa danh mục này? Tất cả giao dịch trong danh mục sẽ đồng thời bị xóa.");
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                try {
                    tranVM.deleteTransById(cat.getId());
                    budVM.deleteById(cat.getId());
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

        ibtnBudget.setOnClickListener(view -> {
            int user_id = context.getIntent().getIntExtra("user_id", 0);
            showDialog(inflater, cat.getId(),user_id);
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

    public void showDialog(LayoutInflater inflater, int cid, int uid) {
        View dialog = inflater.inflate(R.layout.budget_dialog, null);

        EditText txtAmount = dialog.findViewById(R.id.txtAmount);
        TextView txtStartDate = dialog.findViewById(R.id.txtStartDate);
        TextView txtEndDate = dialog.findViewById(R.id.txtEndDate);
        ImageButton ibtnStartDate = dialog.findViewById(R.id.ibtnStartDate);
        ImageButton ibtnEndDate = dialog.findViewById(R.id.ibtnEndDate);
        Calendar sCalendar = Calendar.getInstance();
        Calendar eCalendar = Calendar.getInstance();

        ibtnStartDate.setOnClickListener(view1 -> {
            DateConverter.dateProcessor(txtStartDate, sCalendar, context);
        });
        ibtnEndDate.setOnClickListener(view1 -> {
            DateConverter.dateProcessor(txtEndDate, eCalendar, context);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialog);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int amt = Integer.parseInt(txtAmount.getText().toString());
                Date sDate = sCalendar.getTime();
                Date eDate = eCalendar.getTime();
                Budget bud = new Budget(amt,amt,sDate,eDate,cid,uid);
                budVM.insert(bud);
            }
        });
        builder.setNegativeButton("Trở lại", null);
        builder.setCancelable(false);
        builder.show();
    }

    public void setCats(List<Category> cats) {
        this.objects.clear();
        this.objects.addAll(cats);
        notifyDataSetChanged();
    }
}
