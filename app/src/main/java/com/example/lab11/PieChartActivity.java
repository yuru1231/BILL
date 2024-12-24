package com.example.lab11;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart); // 替换为你的布局文件名称

        // 动态获取 PieChartView 并设置数据
        PieChartView pieChartView = findViewById(R.id.pieChartView);
        float[] data = {50f, 25f, 15f, 10f};
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.rgb(255, 165, 0)};
        String[] labels = {"A", "B", "C", "D"};
        pieChartView.setData(data, colors, labels);
    }
}
