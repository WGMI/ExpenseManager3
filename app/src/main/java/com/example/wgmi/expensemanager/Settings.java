package com.example.wgmi.expensemanager;


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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    View view;
    DBHandler handler;
    Context context;
    Dialog dialog;
    String type,category_name;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.settings, container, false);
        context = this.getActivity();
        handler = new DBHandler(context);
        Button add = (Button) view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.new_category_dialog);
                dialog.show();

                Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner3);
                final EditText et_name = (EditText) dialog.findViewById(R.id.name);
                Button add = (Button) dialog.findViewById(R.id.add);

                List<String> types = new ArrayList<String>();
                types.add("Income");
                types.add("Expense");
                ArrayAdapter<String> adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,types);
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

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(et_name.length() == 0){
                            et_name.setError("Name is required");
                        } else{
                            category_name = et_name.getText().toString();
                            if(handler.getCategoryByName(category_name,type)){
                                et_name.setError("That category already exists");
                            } else{
                                handler.addCategory(new Category(category_name,type));
                                Toast.makeText(context,"New " + type + " category '" + category_name + "' added",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }
                    }
                });
            }
        });
        return view;
    }
}
