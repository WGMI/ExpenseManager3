import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgmi.expensemanager.R;
import com.example.wgmi.expensemanager.Transaction;

import java.util.List;

/**
 * Created by WGMI on 23/02/2017.
 */

public class CustomAdapter extends ArrayAdapter<Transaction> {

    Activity context;
    List<Transaction> transactionList;

    public CustomAdapter(Context context, int resource, List<Transaction> transactionList) {
        super(context, R.layout.custom_list, transactionList);
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = convertView;
        listView = inflater.inflate(R.layout.custom_list,null,true);
        Transaction transaction = transactionList.get(position);

        TextView category = (TextView) listView.findViewById(R.id.category);
        TextView amount = (TextView) listView.findViewById(R.id.amount);
        TextView date = (TextView) listView.findViewById(R.id.id);

        category.setText(transaction.get_category());
        amount.setText(String.valueOf(transaction.get_amount()));
        //date.setText(transaction.get_date());

        return listView;
    }
}
