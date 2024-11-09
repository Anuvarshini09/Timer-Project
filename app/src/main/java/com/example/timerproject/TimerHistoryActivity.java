package com.example.timerproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class TimerHistoryActivity extends AppCompatActivity {
    private TimerHistoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_history);

        dbHelper = new TimerHistoryDatabaseHelper(this);

        ListView historyListView = findViewById(R.id.historyListView);
        Cursor cursor = dbHelper.getAllHistory();

        String[] fromColumns = { "duration", "end_time" };
        int[] toViews = { R.id.textDuration, R.id.textEndTime };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.history_item, cursor, fromColumns, toViews, 0);
        historyListView.setAdapter(adapter);
    }
}