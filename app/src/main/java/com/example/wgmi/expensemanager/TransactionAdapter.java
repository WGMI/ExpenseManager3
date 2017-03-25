package com.example.wgmi.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WGMI on 23/02/2017.
 */

class TransactionAdapter extends BaseAdapter {

    List<Transaction> transactions;
    Context context;
    LayoutInflater inflater;

    public TransactionAdapter(Context context, List<Transaction> t){
        this.context = context;
        this.transactions = t;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list,parent,false);

        TextView _category = (TextView) convertView.findViewById(R.id.category);
        TextView _amount = (TextView) convertView.findViewById(R.id.amount);
        TextView _date = (TextView) convertView.findViewById(R.id.id);
        View v = convertView.findViewById(R.id.type);

        _category.setText(transactions.get(position).get_category());
        _amount.setText(String.valueOf(transactions.get(position).get_amount()));
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date d = new Date(transactions.get(position).get_date());
        _date.setText(df.format(d));
        //_date.setText(String.valueOf(transactions.get(position).get_date()));
        v.setBackgroundColor(Color.parseColor("#00AA00"));
        if(transactions.get(position).get_type().equals("expense")){
            v.setBackgroundColor(Color.parseColor("#AA0000"));
        }

        return convertView;
    }
}
