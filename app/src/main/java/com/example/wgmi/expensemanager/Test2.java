package com.example.wgmi.expensemanager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test2 extends AppCompatActivity {

    /*Calendar c;
    TextView dateView;
    Button button;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    */

    Transaction t;
    TextView tx;
    List<Transaction> transactions;
    List<Transaction> fewTransactions;
    List<Transaction> fewerTransactions;
    public List<Double> amounts;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        handler = new DBHandler(this);
        transactions = handler.getTransactionList();
        fewerTransactions = new ArrayList<>();
        tx = (TextView) findViewById(R.id.textView5);

        amounts = new ArrayList<>();
        amounts.add(handler.sumCategory("income","Other","01/01/27","01/01/07"));
        amounts.add(handler.sumCategory("income","Salary","01/01/27","01/01/09"));
        amounts.add(handler.sumCategory("income","Business","01/01/27","01/01/09"));
        amounts.add(handler.sumCategory("income","Odd Jobs","01/01/27","01/01/09"));
        amounts.add(handler.sumCategory("income","Asset Income","01/01/27","01/01/09"));

        //tx.setText(String.valueOf(fewerTransactions.size()) + " " + String.valueOf(fewerTransactions.size()));
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date(transactions.get(1).get_date());
        Date date2 = new Date(transactions.get(2).get_date());

        if(date.after(date2)){
            tx.setText(String.valueOf(transactions.get(1).get_date()));
        } else{
            tx.setText("qwerty");
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 0);
        ca.add(Calendar.DAY_OF_MONTH,-1);
        //ca.add(Calendar.HOUR_OF_DAY,23);
        //ca.add(Calendar.MINUTE,59);

        String a = "";
        for(Transaction t : transactions){
            //a += t.get_category() + "\n";
        }

        for(int x=0;x<amounts.size();x++){
            a += String.valueOf(amounts.get(x)) + " ";
        }

        tx.setText(a);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            a = bundle.getString("type") + " " + bundle.getString("from") + " " + bundle.getString("to");
            a = a.toLowerCase();
        }

        tx.setText(a);
        /*c = Calendar.getInstance();
        dateView = (TextView) findViewById(R.id.dateView);
        button = (Button) findViewById(R.id.button);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                mDateSetListener,
                mYear,
                mMonth,
                mDay
        );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String dateString = day + "/" + month + "/" + year;
                try {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = df.parse(dateString);
                    long dateSelected = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    /*public void updateDisplay(){
        int m = mMonth + 1;
        dateView.setText(mDay + "/" + m + "/" + mYear);
    }*/
}
