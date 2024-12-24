package com.example.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PieChartView extends View {
    private Paint paint; // 画笔
    private float[] data = {50f, 30f, 20f, 10f}; // 每一部分的百分比
    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.rgb(255, 165, 0)}; // 每部分的颜色
    private String[] labels = {"A", "B", "C", "D"}; // 标签

    // 构造函数，用于 XML
    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true); // 平滑效果
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || data.length == 0) {
            return; // 无数据时不绘制
        }

        float total = 0;
        for (float value : data) {
            total += value;
        }

        float startAngle = 0;
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = Math.min(centerX, centerY) - 20;

        // 绘制饼图
        for (int i = 0; i < data.length; i++) {
            float sweepAngle = data[i] / total * 360;
            paint.setColor(colors[i]);
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                    startAngle, sweepAngle, true, paint);

            // 在扇形中心绘制标签
            float textAngle = startAngle + sweepAngle / 2; // 计算扇形中间角度
            float textRadius = radius / 2; // 标签位置在半径的中间
            float textX = (float) (centerX + textRadius * Math.cos(Math.toRadians(textAngle)));
            float textY = (float) (centerY + textRadius * Math.sin(Math.toRadians(textAngle)));

            paint.setColor(Color.BLACK); // 标签颜色
            paint.setTextSize(50);
            canvas.drawText(labels[i], textX, textY, paint); // 绘制标签

            startAngle += sweepAngle;
        }
    }

    // 更新数据的方法（支持动态更新）
    public void setData(float[] newData, int[] newColors, String[] newLabels) {
        if (newData.length != newColors.length || newColors.length != newLabels.length) {
            throw new IllegalArgumentException("Data, colors, and labels must have the same length");
        }
        this.data = newData;
        this.colors = newColors;
        this.labels = newLabels;
        invalidate(); // 重绘
    }
}
