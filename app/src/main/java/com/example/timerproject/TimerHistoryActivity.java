package com.example.timerproject;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimerHistoryActivity extends AppCompatActivity {
    private TimerHistoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_history);

        dbHelper = new TimerHistoryDatabaseHelper(this);
        ListView historyListView = findViewById(R.id.historyListView);

        // Get all history from the database
        Cursor cursor = dbHelper.getAllHistory();

        if (cursor != null && cursor.getCount() > 0) {
            String[] fromColumns = { "start_time", "end_time", "duration" };
            int[] toViews = { R.id.textStartTime, R.id.textEndTime, R.id.textDuration };
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.history_item, cursor, fromColumns, toViews, 0);
            historyListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No history available", Toast.LENGTH_SHORT).show();
        }

        // Set up the "Done" button to navigate back to MainActivity
        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(v -> navigateBackToHome());
    }

    // Method to navigate back to MainActivity
    private void navigateBackToHome() {
        Intent intent = new Intent(TimerHistoryActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Close the current activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // Close the database connection when activity is destroyed
    }
}