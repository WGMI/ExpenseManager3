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
    PieChart pieChart;
    Double[] y;
    String[] x;// = {"First","Second","Third","Fourth"};
    String type,from,to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        handler = new DBHandler(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            type = bundle.getString("type");
            from = bundle.getString("from");
            to = bundle.getString("to");
        }

        //List<Double> a = handler.getAmountsForChart();
        List<Double> a = handler.getAmountsForChart(type,to,from);
        Double[] amounts = new Double[a.size()];
        a.toArray(amounts);
        y = amounts;

        List<String> b = new ArrayList<>();//handler.getLabelsForChart();
        List<Category> cats = handler.getCategoryListByType(type);
        for(Category c : cats){
            b.add(c.getName());
        }

        String[] labels = new String[b.size()];
        b.toArray(labels);
        x = labels;

        pieChart = (PieChart) findViewById(R.id.chart);

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);

        addDataSet();
    }

    private void addDataSet(){
        ArrayList<Entry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for(int i=0;i<y.length;i++){
            yEntries.add(new Entry(Float.parseFloat(String.valueOf(y[i])),i));
        }

        for(int i=0;i<x.length;i++){
            xEntries.add(x[i]);
        }

        PieDataSet dataset = new PieDataSet(yEntries,type + " Chart");
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

        PieData data = new PieData(xEntries,dataset);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(5000);
    }
}
