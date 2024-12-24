package com.example.lab11;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText ed_goal_name, ed_target_amount;
    private Button  btn_insert, btn_update, btn_delete, btn_start_date_picker, btn_end_date_picker;
    private TextView tv_start_date, tv_end_date;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    private String startDate = ""; // 儲存選擇的開始日期
    private String endDate = "";   // 儲存選擇的結束日期

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbrw.close(); // 關閉資料庫
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化元件
        initializeComponents();

        // 設定資料庫
        dbrw = new MyDBHelper(this).getWritableDatabase();

        // 開始日期選擇器
        btn_start_date_picker.setOnClickListener(view -> showDatePickerDialog(true));

        // 結束日期選擇器
        btn_end_date_picker.setOnClickListener(view -> showDatePickerDialog(false));

        // 新增資料
        btn_insert.setOnClickListener(view -> insertData());



        // 更新資料
        btn_update.setOnClickListener(view -> updateData());

        // 刪除資料
        btn_delete.setOnClickListener(view -> deleteData());

        // 初始化時刷新列表
        refreshListView();
    }

    private void initializeComponents() {
        ed_goal_name = findViewById(R.id.ed_goal_name);
        ed_target_amount = findViewById(R.id.ed_target_amount);

        btn_insert = findViewById(R.id.btn_insert);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_start_date_picker = findViewById(R.id.btn_start_date_picker);
        btn_end_date_picker = findViewById(R.id.btn_end_date_picker);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    if (isStartDate) {
                        startDate = selectedDate;
                        tv_start_date.setText("開始日期: " + startDate);
                    } else {
                        endDate = selectedDate;
                        tv_end_date.setText("結束日期: " + endDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void insertData() {
        if (validateInputs()) {
            try {
                dbrw.execSQL("INSERT INTO savingsTable(goal_name, target_amount, start_date, end_date) VALUES(?, ?, ?, ?)",
                        new Object[]{ed_goal_name.getText().toString(), ed_target_amount.getText().toString(), startDate, endDate});
                Toast.makeText(this, "新增成功: " + ed_goal_name.getText().toString(), Toast.LENGTH_SHORT).show();
                clearFields();
                refreshListView(); // 新增成功後刷新列表
            } catch (Exception e) {
                Log.e("DatabaseError", "新增失敗", e);
                Toast.makeText(this, "新增失敗:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void updateData() {
        if (validateInputs()) {
            try {
                dbrw.execSQL("UPDATE savingsTable SET target_amount = ?, start_date = ?, end_date = ? WHERE goal_name LIKE ?",
                        new Object[]{ed_target_amount.getText().toString(), startDate, endDate, ed_goal_name.getText().toString()});
                Toast.makeText(this, "更新成功: " + ed_goal_name.getText().toString(), Toast.LENGTH_SHORT).show();
                clearFields();
                refreshListView(); // 更新成功後刷新列表
            } catch (Exception e) {
                Log.e("DatabaseError", "更新失敗", e);
                Toast.makeText(this, "更新失敗:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteData() {
        if (ed_goal_name.length() < 1) {
            Toast.makeText(this, "請輸入目標名稱", Toast.LENGTH_SHORT).show();
        } else {
            try {
                dbrw.execSQL("DELETE FROM savingsTable WHERE goal_name LIKE ?", new Object[]{ed_goal_name.getText().toString()});
                Toast.makeText(this, "刪除成功: " + ed_goal_name.getText().toString(), Toast.LENGTH_SHORT).show();
                clearFields();
                refreshListView(); // 刪除成功後刷新列表
            } catch (Exception e) {
                Log.e("DatabaseError", "刪除失敗", e);
                Toast.makeText(this, "刪除失敗:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs() {
        if (ed_goal_name.length() < 1 || ed_target_amount.length() < 1 || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "所有欄位請勿留空", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(ed_target_amount.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "目標金額必須為有效的整數", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void refreshListView() {
        Cursor c = dbrw.rawQuery("SELECT * FROM savingsTable", null);
        items.clear(); // 清空現有項目
        while (c.moveToNext()) {
            String goalName = c.getString(0);
            int targetAmount = c.getInt(1);
            String startDate = c.getString(2);
            String endDate = c.getString(3);

            items.add("目標: " + goalName +
                    "\n金額: " + targetAmount +
                    "\n開始: " + startDate +
                    "\n結束: " + endDate);
        }
        adapter.notifyDataSetChanged(); // 更新 ListView
        c.close();
    }

    private void clearFields() {
        ed_goal_name.setText("");
        ed_target_amount.setText("");
        tv_start_date.setText("尚未選擇開始日期");
        tv_end_date.setText("尚未選擇結束日期");
        startDate = "";
        endDate = "";
    }
}
