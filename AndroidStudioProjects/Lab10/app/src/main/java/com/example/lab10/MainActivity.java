package com.example.lab10;

// 第一頁: 用於選擇地點
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final LatLng[] predefinedLocations = {
            new LatLng(25.033611, 121.565000), // 台北101
            new LatLng(25.047924, 121.517081), // 台北車站
            new LatLng(25.032728, 121.564137),  // 臺北信義區
            new LatLng(25.02348, 121.52864),  // 全家便利商店
            new LatLng(25.04360, 121.53562),  // 7-11
            new LatLng(25.05591, 121.51970) , // 中山商圈
            new LatLng(25.03369, 121.52998)  // 永康商圈
    };
    private final String[] predefinedLocationNames = {"台北101", "台北車站", "臺北信義區","全家便利商店","7-11","中山商圈","永康商圈"};
    private final List<LatLng> selectedLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 新增地點按鈕
        Button addLocationButton = findViewById(R.id.add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptUserToSelectLocation();
            }
        });

        // 查看地點按鈕
        Button viewLocationsButton = findViewById(R.id.view_locations_button);
        viewLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewLocation.class);
                intent.putParcelableArrayListExtra("locations", new ArrayList<>(selectedLocations));
                startActivity(intent);
            }
        });
    }

    private void promptUserToSelectLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("選擇地點");

        final Spinner locationSpinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                predefinedLocationNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        builder.setView(locationSpinner);

        builder.setPositiveButton("新增標記", (dialog, which) -> {
            int selectedPosition = locationSpinner.getSelectedItemPosition();
            LatLng selectedLocation = predefinedLocations[selectedPosition];

            selectedLocations.add(selectedLocation);
            Toast.makeText(this, "地點已新增", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}