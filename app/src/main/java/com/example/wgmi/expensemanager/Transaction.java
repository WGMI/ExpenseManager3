package com.example.wgmi.expensemanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WGMI on 22/02/2017.
 */

public class Transaction {

    int _id;
    double _amount;
    String _category,_notes,_type;
    Long _date;

    public Transaction(){

    }

    public Transaction(int _id, double _amount, String _category, Long _date, String _notes, String _type) {
        this._id = _id;
        this._amount = _amount;
        this._category = _category;
        this._date = _date;
        this._notes = _notes;
        this._type = _type;
    }

    public Transaction(double _amount, String _category, Long _date, String _notes, String _type) {
        this._amount = _amount;
        this._category = _category;
        this._date = _date;
        this._notes = _notes;
        this._type = _type;
    }

    public int get_id() {
        return _id;
    }

    public double get_amount() {
        return _amount;
    }

    public String get_category() {
        return _category;
    }

    public Long get_date() {
        return _date;
    }

    public String get_notes() {
        return _notes;
    }

    public String get_type() {
        return _type;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public void set_date(Long _date) {
        this._date = _date;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public void set_type(String _type) {
        this._notes = _type;
    }
}
