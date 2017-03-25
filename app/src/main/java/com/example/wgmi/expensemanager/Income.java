package com.example.wgmi.expensemanager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Income extends Fragment {

    DBHandler handler;
    Context context;
    Dialog dialog;

    public Income() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income,container,false);
        handler = new DBHandler(this.getActivity());
        context = this.getActivity();

        if(handler.countTransactionsByType("income") == 0){
            return view;
        }

        if(handler.countTransactions() == 0){
            return view;
        } else{
            final ListView list = (ListView) view.findViewById(R.id.list);
            final List<Transaction> transactions = handler.getIncomesOrExpense("income");

            TransactionAdapter adapter = new TransactionAdapter(context,transactions);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Transaction tr = transactions.get(position);
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_dialog);

                    final TextView category,amount,date,notes;
                    ImageView edit,delete;

                    category = (TextView) dialog.findViewById(R.id.category);
                    amount = (TextView) dialog.findViewById(R.id.amount);
                    date = (TextView) dialog.findViewById(R.id.date);
                    notes = (TextView) dialog.findViewById(R.id.notes);
                    edit = (ImageView) dialog.findViewById(R.id.edit);
                    delete = (ImageView) dialog.findViewById(R.id.delete);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",tr.get_id());
                            Fragment newIncome = new NewIncome();
                            newIncome.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(((ViewGroup)getView().getParent()).getId(), newIncome); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();
                            dialog.cancel();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Delete entry")
                                    .setMessage("This entry will be permanently lost." + "\n" + "Are you sure you want to continue?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            handler.delete(tr);
                                            Fragment frg  = getFragmentManager().findFragmentById(((ViewGroup)getView().getParent()).getId());
                                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.detach(frg);
                                            ft.attach(frg);
                                            ft.commit();
                                            dialog.cancel();
                                            Income.this.dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    });

                    category.setText(tr.get_category());
                    amount.setText(String.valueOf(tr.get_amount()));
                    date.setText(new SimpleDateFormat("dd/MM/yy").format(new Date(tr.get_date())));
                    //date.setText(String.valueOf(tr.get_date()));
                    notes.setText("\n\n" + tr.get_notes() + "\n\n");

                    dialog.show();
                }
            });
            return view;
        }
    }
}
