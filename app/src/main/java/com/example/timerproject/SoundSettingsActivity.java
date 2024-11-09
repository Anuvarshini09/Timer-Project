package com.example.timerproject;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SoundSettingsActivity extends AppCompatActivity {
    private int selectedSound;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_settings);

        Button sound1Button = findViewById(R.id.sound1);
        Button sound2Button = findViewById(R.id.sound2);
        Button sound3Button = findViewById(R.id.sound3);

        sound1Button.setOnClickListener(v -> previewSound(R.raw.sound1));
        sound2Button.setOnClickListener(v -> previewSound(R.raw.sound2));
        sound3Button.setOnClickListener(v -> previewSound(R.raw.sound3));

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveSelectedSound());
    }

    private void previewSound(int soundResId) {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, soundResId);
        mediaPlayer.start();
        selectedSound = soundResId;
    }

    private void saveSelectedSound() {
        SharedPreferences prefs = getSharedPreferences("SoundSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("selectedSound", selectedSound);
        editor.apply();
    }
}