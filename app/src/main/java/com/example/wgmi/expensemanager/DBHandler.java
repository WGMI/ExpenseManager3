package com.example.wgmi.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by WGMI on 22/02/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB = "ExpenseManager";
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String CATEGORY = "category";
    private static final String DATE = "date";
    private static final String NOTE = "note";
    private static final String TYPE = "type";

    public DBHandler(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TRANSACTIONS_TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AMOUNT + " DOUBLE,"
                + CATEGORY + " TEXT,"
                + DATE + " LONG,"
                + NOTE + " TEXT,"
                + TYPE + " TEXT"
                + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
        onCreate(db);
    }

    public void addTransaction(Transaction transaction){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT,transaction.get_amount());
        values.put(CATEGORY,transaction.get_category());
        values.put(DATE,transaction.get_date());
        values.put(NOTE,transaction.get_notes());
        values.put(TYPE,transaction.get_type());
        db.insert(TRANSACTIONS_TABLE,null,values);
    }

    public Transaction getTransaction(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Transaction transaction = new Transaction(cursor.getInt(0),cursor.getDouble(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getString(5));
        cursor.close();
        return transaction;
    }

    public List<Transaction> getIncomesOrExpense(String type){
        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TRANSACTIONS_TABLE + " WHERE type = '" + type + "' ORDER BY " + DATE + " DESC",null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        for(int i=0;i<countTransactions();i++){
            Transaction transaction = new Transaction(cursor.getInt(0),cursor.getDouble(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getString(5));
            cursor.moveToNext();
            transactions.add(transaction);
            if(cursor.isAfterLast()){
                break;
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionList(){
        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM " + TRANSACTIONS_TABLE + " ORDER BY " + DATE + " DESC";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        for(int i=0;i<this.countTransactions();i++){
            Transaction transaction = new Transaction(cursor.getInt(0),cursor.getDouble(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getString(5));
            cursor.moveToNext();
            transactions.add(transaction);
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByCategory(String type, String category){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TRANSACTIONS_TABLE + " WHERE type = '" + type + "' AND category = '" + category + "'",null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        for(int i=0;i<countTransactions();i++){
            Transaction transaction = new Transaction(cursor.getInt(0),cursor.getDouble(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getString(5));
            cursor.moveToNext();
            transactions.add(transaction);
            if(cursor.isAfterLast()){
                break;
            }
        }

        return transactions;
    }

    public double sumCategory(String type, String category, String before, String after){
        SQLiteDatabase db = this.getReadableDatabase();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        try {
            Date beforeDate = df.parse(before);
            Date afterDate = df.parse(after);
            Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TRANSACTIONS_TABLE + " WHERE type = '" + type + "' AND category = '" + category + "' AND date <= " + beforeDate.getTime() + " AND date >= " + afterDate.getTime(),null);
            if(cursor != null){
                cursor.moveToFirst();
            }
            return cursor.getDouble(0);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countTransactions(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TRANSACTIONS_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            return cursor.getCount();
        } else{
            return 0;
        }
    }

    public int countTransactionsByType(String type){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TRANSACTIONS_TABLE + " WHERE type = '" + type + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            return cursor.getCount();
        } else{
            return 0;
        }
    }

    public int updateRecord(Transaction transaction){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT,transaction.get_amount());
        values.put(CATEGORY,transaction.get_category());
        values.put(DATE,transaction.get_date());
        values.put(NOTE,transaction.get_notes());
        values.put(TYPE,transaction.get_type());
        return db.update(
                TRANSACTIONS_TABLE,
                values,
                ID + " = ?",
                new String[] {String.valueOf(transaction.get_id())}
        );
    }

    public void delete(Transaction transaction){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                TRANSACTIONS_TABLE,
                ID + " = ?",
                new String[] {String.valueOf(transaction.get_id())}
        );
        db.close();
    }
}
