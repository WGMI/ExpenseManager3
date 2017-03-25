package com.example.wgmi.expensemanager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Charts extends AppCompatActivity {

    DBHandler handler;
    public List<Double> amounts;
    public String[] categories;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        handler = new DBHandler(this);

        amounts = new ArrayList<>();
        amounts.add(handler.sumCategory("income","Other","01/01/18","01/01/17"));
        amounts.add(handler.sumCategory("income","Salary","01/01/18","01/01/17"));
        amounts.add(handler.sumCategory("income","Business","01/01/18","01/01/17"));
        amounts.add(handler.sumCategory("income","Odd Jobs","01/01/18","01/01/17"));
        amounts.add(handler.sumCategory("income","Asset Income","01/01/18","01/01/17"));
        /*amounts.add(12d);
        amounts.add(12d);
        amounts.add(12d);
        amounts.add(12d);
        amounts.add(12d);*/

        categories = new String[5];
        categories[0] = "Other";
        categories[1] = "Salary";
        categories[2] = "Business";
        categories[3] = "Odd Jobs";
        categories[4] = "Asset Income";

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                Toast.makeText(Charts.this,entry.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        /*ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry())

        PieDataSet dataset = new PieDataSet(entries, "Expenses");

        ArrayList<String> labels = new ArrayList<String>();
        for(Transaction t : transactions){
            labels.add(t.get_category());
        }

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);*/
    }

    private void addDataSet() {
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        for(int x=0;x<amounts.size();x++){
            yValues.add(new Entry(Float.parseFloat(String.valueOf(amounts.get(x))),x));
            xValues.add(categories[x]);
        }

        PieDataSet dataset = new PieDataSet(yValues,"Income");
        dataset.setSliceSpace(0);
        dataset.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(new Random().nextInt());
        colors.add(new Random().nextInt());
        colors.add(new Random().nextInt());
        colors.add(new Random().nextInt());
        colors.add(new Random().nextInt());
        colors.add(new Random().nextInt());

        dataset.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData data = new PieData(xValues,dataset);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(5000);
    }
}
