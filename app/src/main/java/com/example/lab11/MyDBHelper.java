package com.example.lab11;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB.db"; // 資料庫名稱
    private static final int DATABASE_VERSION = 3; // 資料庫版本

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建立 myTable 資料表
        db.execSQL("CREATE TABLE myTable (" +
                "book TEXT, " +
                "price INTEGER, " +
                "year INTEGER, " +
                "month INTEGER, " +
                "day INTEGER)");

        // 建立 deleted_categories 資料表
        db.execSQL("CREATE TABLE deleted_categories (" +
                "book TEXT PRIMARY KEY)");

        // 建立 locations 資料表
        db.execSQL("CREATE TABLE locations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "latitude REAL NOT NULL, " +
                "longitude REAL NOT NULL, " +
                "added_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS deleted_categories (" +
                    "book TEXT PRIMARY KEY)");
        }
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS locations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, " +
                    "added_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    // 插入地點
    public void insertLocation(String name, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO locations (name, latitude, longitude) VALUES (?, ?, ?)",
                new Object[]{name, latitude, longitude});
        db.close();
    }

    // 獲取所有地點
    public List<LatLng> getAllLocations() {
        List<LatLng> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT latitude, longitude FROM locations", null);

        while (cursor.moveToNext()) {
            double lat = cursor.getDouble(0);
            double lng = cursor.getDouble(1);
            locations.add(new LatLng(lat, lng));
        }
        cursor.close();
        db.close();
        return locations;
    }
}
