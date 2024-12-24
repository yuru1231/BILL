package com.example.lab11;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class bill extends AppCompatActivity {

    private EditText ed_price;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    private Button btn_insert, btn_delete, btn_add_location, btn_view_locations;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    private Spinner yearSpinner, monthSpinner, daySpinner;

    private final LatLng[] predefinedLocations = {
            new LatLng(25.033611, 121.565000),
            new LatLng(25.047924, 121.517081),
            new LatLng(25.032728, 121.564137),
            new LatLng(25.02348, 121.52864),
            new LatLng(25.04360, 121.53562),
            new LatLng(25.05591, 121.51970),
            new LatLng(25.03369, 121.52998)
    };
    private final String[] predefinedLocationNames = {"台北101", "台北車站", "臺北信義區", "全家便利商店", "7-11", "中山商圈", "永康商圈"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);

        ed_price = findViewById(R.id.ed_price);
        btn_add_location = findViewById(R.id.add_location_button);
        btn_view_locations = findViewById(R.id.view_locations_button);

        btn_add_location.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("選擇地點");

            final Spinner locationSpinner = new Spinner(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, predefinedLocationNames);
            locationSpinner.setAdapter(adapter);

            builder.setView(locationSpinner);
            builder.setPositiveButton("新增標記", (dialog, which) -> {
                int selectedPosition = locationSpinner.getSelectedItemPosition();
                LatLng selectedLocation = predefinedLocations[selectedPosition];
                String selectedName = predefinedLocationNames[selectedPosition];

                MyDBHelper dbHelper = new MyDBHelper(this);
                dbHelper.insertLocation(selectedName, selectedLocation.latitude, selectedLocation.longitude);
                Toast.makeText(this, "地點已新增: " + selectedName, Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        btn_view_locations.setOnClickListener(view -> {
            Intent intent = new Intent(bill.this, ViewLocation.class);
            startActivity(intent);
        });
    }
}