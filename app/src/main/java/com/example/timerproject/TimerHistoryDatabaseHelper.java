package com.example.timerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimerHistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TimerHistory.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "timer_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_END_TIME = "end_time";

    public TimerHistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_END_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTimerHistory(String duration, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_END_TIME, endTime);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
