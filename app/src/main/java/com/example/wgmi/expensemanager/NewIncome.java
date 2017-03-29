package com.example.wgmi.expensemanager;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIncome extends Fragment {

    View view;
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DatePickerDialog datePickerDialog;

    long dateSelected = date.getTime();
    String category = "Other";
    String notes = "No notes";
    DBHandler handler;
    Bundle bundle;
    String dt = df.format(new Date());
    int id;

    public NewIncome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_income,container,false);
        handler = new DBHandler(this.getActivity());

        bundle = getArguments();

        final EditText et_amount = (EditText) view.findViewById(R.id.amount);
        final EditText et_notes = (EditText) view.findViewById(R.id.notes);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final TextView dateView = (TextView) view.findViewById(R.id.id);
        dateView.setText(df.format(new Date(dateSelected)));
        Button save = (Button) view.findViewById(R.id.save);
        final Button cancel = (Button) view.findViewById(R.id.cancel);

        List<Category> allCategories = handler.getCategoryList();
        List<String> categories = new ArrayList<>();

        for(Category c : allCategories){
            if(c.getType().equals("Income")){
                categories.add(c.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Context context = this.getActivity();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int m = month + 1;
                String d = dayOfMonth + "/" + m + "/" + year;
                dt = d;
                dateView.setText(d);
            }
        };

        datePickerDialog = new DatePickerDialog(
                context,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                try {
                    Date date = df.parse(day + "/" + month + "/" + year);
                    dateSelected = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        if(bundle != null){
            id = bundle.getInt("id");
            Transaction t = handler.getTransaction(id);
            et_amount.setText(String.valueOf(t.get_amount()));

            int index = 0;
            for (int i=0;i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).equals(t.get_category())){
                    index = i;
                }
            }

            spinner.setSelection(index);
            dateView.setText(df.format(new Date(t.get_date())));
            et_notes.setText(t.get_notes());
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,Overview.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_amount.length() == 0){
                    et_amount.setError("Amount is required");
                } else{
                    if(bundle != null){
                        float amount = Float.parseFloat(et_amount.getText().toString());
                        String cat = category;
                        Long date = null;
                        try {
                            date = df.parse(dt).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(et_notes.getText().toString().length() != 0){
                            notes = et_notes.getText().toString();
                        }

                        String type = "income";
                        handler.updateRecord(new Transaction(id,amount,cat,date,notes,type));
                        Fragment income = new Income();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(((ViewGroup)getView().getParent()).getId(),income);
                        transaction.commit();
                    } else{
                        float amount = Float.parseFloat(et_amount.getText().toString());
                        String cat = category;
                        Long date = null;
                        try {
                            date = df.parse(dt).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(et_notes.getText().toString().length() != 0){
                            notes = et_notes.getText().toString();
                        }

                        String type = "income";
                        handler.addTransaction(new Transaction(amount,cat,date,notes,type));
                        startActivity(new Intent(context,Overview.class));
                    }
                }
            }
        });

        //Remember to delete
        save.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(context,Test.class));
                return false;
            }
        });

        return view;
    }
}
