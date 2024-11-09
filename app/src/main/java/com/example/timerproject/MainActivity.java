package com.example.timerproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText hoursInput, minutesInput, secondsInput;
    private TextView timerDisplay;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeInMillis;
    private TimerHistoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TimerHistoryDatabaseHelper(this);

        hoursInput = findViewById(R.id.hoursInput);
        minutesInput = findViewById(R.id.minutesInput);
        secondsInput = findViewById(R.id.secondsInput);
        timerDisplay = findViewById(R.id.timerDisplay);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());

        Button soundSettingsButton = findViewById(R.id.soundSettingsButton);
        soundSettingsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SoundSettingsActivity.class)));

        Button historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TimerHistoryActivity.class)));
    }

    private void startTimer() {
        if (!isRunning) {
            int hours = Integer.parseInt(hoursInput.getText().toString()) * 3600;
            int minutes = Integer.parseInt(minutesInput.getText().toString()) * 60;
            int seconds = Integer.parseInt(secondsInput.getText().toString());
            timeInMillis = (hours + minutes + seconds) * 1000;

            countDownTimer = new CountDownTimer(timeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerDisplay.setText(formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    timerDisplay.setText("00:00:00");
                    playNotificationSound();
                    saveTimerHistory("Duration: " + formatTime(timeInMillis), getCurrentTime());
                    Toast.makeText(MainActivity.this, "Time’s up!", Toast.LENGTH_SHORT).show();
                }
            }.start();
            isRunning = true;
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timerDisplay.setText("00:00:00");
        isRunning = false;
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void playNotificationSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound);
        mediaPlayer.start();
    }

    private void saveTimerHistory(String duration, String endTime) {
        dbHelper.addTimerHistory(duration, endTime);
    }
}