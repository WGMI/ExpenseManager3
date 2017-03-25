package com.example.wgmi.expensemanager;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    View view;
    TextView income_today,expense_today,income_tm,expense_tm,total_today,total_tm;
    Context context;
    DBHandler handler;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this.getActivity();
        handler = new DBHandler(context);

        //List<Transaction> income = handler.getIncomesOrExpense("income");
        //List<Transaction> expense = handler.getIncomesOrExpense("expense");

        List<Transaction> transactions = handler.getTransactionList();

        List<Transaction> transactionsToday = new ArrayList<>();
        List<Transaction> incomeToday = new ArrayList<>();
        List<Transaction> expenseToday = new ArrayList<>();

        List<Transaction> transactionsTM = new ArrayList<>();
        List<Transaction> incomeTM = new ArrayList<>();
        List<Transaction> expenseTM = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 0);
        ca.add(Calendar.DAY_OF_MONTH,-1);

        for(Transaction t : transactions){
            Date d = new Date(t.get_date());
            if(d.before(c.getTime()) && d.after(ca.getTime())){
                transactionsToday.add(t);
            }
        }

        for(Transaction tr : transactionsToday){
            if(tr.get_type().equals("income")){
                incomeToday.add(tr);
            } else if(tr.get_type().equals("expense")){
                expenseToday.add(tr);
            }
        }

        double totalIncomeToday = 0;
        double totalExpenseToday = 0;

        for(Transaction t : incomeToday){
            totalIncomeToday += t.get_amount();
        }

        for(Transaction t : expenseToday){
            totalExpenseToday += t.get_amount();
        }

        double netToday = totalIncomeToday - totalExpenseToday;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH,1);

        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        for(Transaction t : transactions){
            Date d = new Date(t.get_date());
            if(d.before(cale.getTime()) && d.after(cal.getTime())){
                transactionsTM.add(t);
            }
        }

        for(Transaction tr : transactionsTM){
            if(tr.get_type().equals("income")){
                incomeTM.add(tr);
            } else if(tr.get_type().equals("expense")){
                expenseTM.add(tr);
            }
        }

        double totalIncomeTM = 0;
        double totalExpenseTM = 0;

        for(Transaction t : incomeTM){
            totalIncomeTM += t.get_amount();
        }

        for(Transaction t : expenseTM){
            totalExpenseTM += t.get_amount();
        }

        double netTM = totalIncomeTM - totalExpenseTM;

        /*double[] in = new double[income.size()];
        double[] ex = new double[expense.size()];

        for(int i=0;i<in.length;i++){
            Transaction t = income.get(i);
            in[i] = t.get_amount();
        }

        for(int i=0;i<ex.length;i++){
            Transaction tr = expense.get(i);
            ex[i] = tr.get_amount();
        }*/

        view = inflater.inflate(R.layout.fragment_overview,container,false);
        income_today = (TextView) view.findViewById(R.id.income_today);
        expense_today = (TextView) view.findViewById(R.id.expense_today);
        income_tm = (TextView) view.findViewById(R.id.income_tm);
        expense_tm = (TextView) view.findViewById(R.id.expense_tm);
        total_today = (TextView) view.findViewById(R.id.total_today);
        total_tm = (TextView) view.findViewById(R.id.total_tm);

        income_today.setText(String.valueOf(totalIncomeToday));
        expense_today.setText(String.valueOf(totalExpenseToday));
        total_today.setText(String.valueOf(netToday));
        if(netToday < 0){
            total_today.setTextColor(Color.rgb(200,0,0));
        }

        income_tm.setText(String.valueOf(totalIncomeTM));
        expense_tm.setText(String.valueOf(totalExpenseTM));
        total_tm.setText(String.valueOf(netTM));
        if(netTM < 0){
            total_tm.setTextColor(Color.rgb(200,0,0));
        }

        return view;
    }

}
