package com.example.wgmi.expensemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Test extends AppCompatActivity {

    //TO DO
    /*

    */

    DBHandler handler = new DBHandler(this);
    Transaction t;
    Button button;
    TextView a,c,d,n,i,ty;
    String start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<Transaction> transactions = handler.getTransactionList();

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        button = (Button)findViewById(R.id.button);

        Calendar ca = Calendar.getInstance();
        //ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.DAY_OF_MONTH,ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        //ca.set(Calendar.MINUTE, 0);
        //ca.set(Calendar.SECOND, 0);


        t = transactions.get(0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Dialog dialog = new Dialog(Test.this);
                dialog.setContentView(R.layout.custom_dialog);
                TextView category,amount,date,notes;
                category = (TextView) dialog.findViewById(R.id.category);
                amount = (TextView) dialog.findViewById(R.id.amount);
                date = (TextView) dialog.findViewById(R.id.date);
                notes = (TextView) dialog.findViewById(R.id.notes);

                category.setText(t.get_category());
                amount.setText(String.valueOf(t.get_amount()));
                date.setText(String.valueOf(t.get_date()));
                notes.setText("\n\n" + t.get_notes() + "\n\n");

                dialog.show();*/
                t.set_category("qwerty");
                t.set_amount(7000.0);
                //handler.updateRecord(t);
                finish();
                //startActivity(getIntent());
                startActivity(new Intent(Test.this,Overview.class));
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.addCategory(new Category("Other","income"));
                handler.addCategory(new Category("Salary","income"));
                button.setText(String.valueOf(handler.addCategory(new Category("Salary","income"))));
                return true;
            }
        });

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        //button.setText(handler.getCategory(0).getId() + " " + handler.getCategory(0).getName() + " " + handler.getCategory(0).getType());
        //button.setText(handler.countCategories());

        a = (TextView) findViewById(R.id.amount);
        c = (TextView) findViewById(R.id.category);
        d = (TextView) findViewById(R.id.id);
        n = (TextView) findViewById(R.id.notes);
        ty = (TextView) findViewById(R.id.type);
        i = (TextView) findViewById(R.id._id);

        a.setText("Amount: " + String.valueOf(t.get_amount()));
        c.setText("Category: " + t.get_category());
        d.setText("Date: " + df.format(ca.getTime()));
        n.setText("Note: " + t.get_notes());
        ty.setText("Type: " + t.get_type() + " = type");
        i.setText("ID: " + String.valueOf(t.get_id()));
    }
}
