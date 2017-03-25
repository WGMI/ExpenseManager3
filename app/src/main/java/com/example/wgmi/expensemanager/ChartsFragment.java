package com.example.wgmi.expensemanager;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

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
public class ChartsFragment extends Fragment {

    View view;
    Calendar calendar = Calendar.getInstance();
    Dialog dialog;
    Dialog category_dialog;
    Context context;
    String type;
    Date date = new Date();
    TextView fromDateView,toDateView;
    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DatePickerDialog fromDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    String dt_f = df.format(new Date());
    String dt_t = df.format(new Date());

    long fromDateSelected = date.getTime();
    long toDateSelected = date.getTime();

    public ChartsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.charts, container, false);

        context = this.getActivity();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.chart_type_dialog);
        dialog.show();

        final TextView category,time;
        category = (TextView) dialog.findViewById(R.id.category);
        time = (TextView) dialog.findViewById(R.id.time);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_dialog = new Dialog(context);
                dialog.setContentView(R.layout.category_chart_dialog);
                dialog.show();

                Spinner spinner = (Spinner) dialog.findViewById(R.id.type);
                Button pie,bar;
                pie = (Button) dialog.findViewById(R.id.pie);
                bar = (Button) dialog.findViewById(R.id.bar);

                fromDateView = (TextView) dialog.findViewById(R.id.category_from);
                toDateView = (TextView) dialog.findViewById(R.id.category_to);

                fromDateView.setText(df.format(new Date(fromDateSelected)));
                toDateView.setText(df.format(new Date(toDateSelected)));

                final List<String> types = new ArrayList<>();
                types.add("Expenses");
                types.add("Income");
                types.add("Both");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,types);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        type = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                DatePickerDialog.OnDateSetListener fromlistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m = month + 1;
                        String d = dayOfMonth + "/" + m + "/" + year;
                        dt_f = d;
                        fromDateView.setText(d);
                    }
                };

                DatePickerDialog.OnDateSetListener tolistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m = month + 1;
                        String d = dayOfMonth + "/" + m + "/" + year;
                        dt_t = d;
                        toDateView.setText(d);
                    }
                };

                fromDatePickerDialog = new DatePickerDialog(
                        context,
                        fromlistener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                toDatePickerDialog = new DatePickerDialog(
                        context,
                        tolistener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                fromDateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fromDatePickerDialog.show();
                        DatePicker datePicker = fromDatePickerDialog.getDatePicker();
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        try {
                            Date date = df.parse(day + "/" + month + "/" + year);
                            fromDateSelected = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                toDateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDatePickerDialog.show();
                        DatePicker datePicker = toDatePickerDialog.getDatePicker();
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        try {
                            Date date = df.parse(day + "/" + month + "/" + year);
                            toDateSelected = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                pie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("type",type);
                        bundle.putString("from",fromDateView.getText().toString());
                        bundle.putString("to",toDateView.getText().toString());
                        Intent intent = new Intent(context,Test2.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                bar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context,Charts.class));
                    }
                });
            }
        });

        return view;
    }

}
