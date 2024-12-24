package com.example.lab11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "savingsDatabase.db"; // 資料庫名稱
    private static final int DATABASE_VERSION = 2; // 資料庫版本

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE savingsTable (" +
                "goal_name TEXT PRIMARY KEY, " +    // 儲蓄目標名稱
                "target_amount INTEGER NOT NULL, " + // 目標金額
                "start_date TEXT NOT NULL, " +       // 開始日期
                "end_date TEXT NOT NULL)");          // 結束日期
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS savingsTable");
        onCreate(db);
    }
}
