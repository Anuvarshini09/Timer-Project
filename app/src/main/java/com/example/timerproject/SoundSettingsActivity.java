package com.example.timerproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SoundSettingsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private static final String PREFS_NAME = "timer_prefs";
    private static final String KEY_SELECTED_SOUND = "selected_sound";
    private int selectedSoundResource = R.raw.sound1; // Default sound

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);

        Button sound1Button = findViewById(R.id.sound1);
        Button sound2Button = findViewById(R.id.sound2);
        Button sound3Button = findViewById(R.id.sound3);
        Button saveButton = findViewById(R.id.saveButton);

        // Set the selected sound when a sound button is clicked
        sound1Button.setOnClickListener(v -> selectSound(R.raw.sound1));
        sound2Button.setOnClickListener(v -> selectSound(R.raw.sound2));
        sound3Button.setOnClickListener(v -> selectSound(R.raw.sound3));

        // Save the selected sound and navigate back to MainActivity when "Save Selected" is clicked
        saveButton.setOnClickListener(v -> {
            saveSelectedSound();
            navigateBackToMain();
        });
    }

    private void selectSound(int soundResourceId) {
        // Set the selected sound
        selectedSoundResource = soundResourceId;

        // Preview the selected sound
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }

    private void saveSelectedSound() {
        // Save the selected sound to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_SELECTED_SOUND, selectedSoundResource);
        editor.apply();
        Toast.makeText(this, "Sound selected and saved", Toast.LENGTH_SHORT).show();
    }

    private void navigateBackToMain() {
        // Return to MainActivity
        Intent intent = new Intent(SoundSettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Close SoundSettingsActivity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}