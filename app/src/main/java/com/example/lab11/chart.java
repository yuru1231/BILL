package com.example.lab11;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class chart extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);

        // 綁定 XML 中的圖表控件
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);

        // 初始化並顯示圖表
        setupPieChart();
        loadPieChartData();

        setupBarChart();
        loadBarChartData();
    }

    // 配置圓餅圖
    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setCenterText("支出比例");
        pieChart.setCenterTextSize(16f);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
    }

    // 加載圓餅圖數據
    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "食品"));
        entries.add(new PieEntry(30f, "交通"));
        entries.add(new PieEntry(20f, "娛樂"));
        entries.add(new PieEntry(10f, "其他"));

        PieDataSet dataSet = new PieDataSet(entries, "支出類別");
        dataSet.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW});
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // 刷新圖表
    }

    // 配置長條圖
    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true); // 確保長條對齊
        barChart.setDrawGridBackground(false);
    }

    // 加載長條圖數據
    private void loadBarChartData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, 5000f)); // 一月
        entries.add(new BarEntry(2f, 3000f)); // 二月
        entries.add(new BarEntry(3f, 7000f)); // 三月
        entries.add(new BarEntry(4f, 4000f)); // 四月
        entries.add(new BarEntry(5f, 6000f)); // 五月

        BarDataSet dataSet = new BarDataSet(entries, "月支出");
        dataSet.setColors(Color.parseColor("#FFA726")); // 橙色
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate(); // 刷新圖表
    }
}
