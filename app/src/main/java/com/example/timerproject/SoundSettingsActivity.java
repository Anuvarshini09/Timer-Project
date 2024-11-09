package com.example.timerproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SoundSettingsActivity extends AppCompatActivity {
    private int selectedSound;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);

        // Buttons to select sounds
        Button sound1Button = findViewById(R.id.sound1);
        Button sound2Button = findViewById(R.id.sound2);
        Button sound3Button = findViewById(R.id.sound3);
        Button saveButton = findViewById(R.id.saveButton);

        // Set onClick listeners for sound buttons to preview sounds
        sound1Button.setOnClickListener(v -> previewSound(R.raw.sound1));
        sound2Button.setOnClickListener(v -> previewSound(R.raw.sound2));
        sound3Button.setOnClickListener(v -> previewSound(R.raw.sound3));

        // Set onClick listener for save button to save the selected sound
        saveButton.setOnClickListener(v -> saveSelectedSound());
    }

    // Method to preview the selected sound
    private void previewSound(int soundResId) {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release any previously playing sound
        }
        mediaPlayer = MediaPlayer.create(this, soundResId);
        mediaPlayer.start();
        selectedSound = soundResId; // Update the selected sound
    }

    // Method to save the selected sound and navigate back to MainActivity
    private void saveSelectedSound() {
        if (selectedSound == 0) {
            Toast.makeText(this, "Please select a sound before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the selected sound in SharedPreferences
        SharedPreferences prefs = getSharedPreferences("SoundSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("selectedSound", selectedSound);
        editor.apply();

        Toast.makeText(this, "Sound selection saved!", Toast.LENGTH_SHORT).show();

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
            mediaPlayer.release(); // Release the MediaPlayer when the activity is destroyed
            mediaPlayer = null;
        }
    }
}