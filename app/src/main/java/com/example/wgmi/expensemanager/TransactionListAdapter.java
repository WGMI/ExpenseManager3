package com.example.wgmi.expensemanager;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by WGMI on 23/02/2017.
 */

class TransactionListAdapter extends BaseAdapter {

    List<Transaction> transactions;

    public TransactionListAdapter(List<Transaction> t){
        this.transactions = t;
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
        LayoutInflater inflater;// = getActivity().getLayoutInflater();
        //convertView = inflater.inflate(R.layout.custom_list,parent,false);

        TextView _category = (TextView) convertView.findViewById(R.id.category);
        TextView _amount = (TextView) convertView.findViewById(R.id.amount);
        TextView _date = (TextView) convertView.findViewById(R.id.id);
        View v = convertView.findViewById(R.id.type);

        _category.setText(transactions.get(position).get_category());
        _amount.setText(String.valueOf(transactions.get(position).get_amount()));
        _date.setText(String.valueOf(transactions.get(position).get_date()));
        v.setBackgroundColor(Color.parseColor("#00AA00"));

        return convertView;
    }
}