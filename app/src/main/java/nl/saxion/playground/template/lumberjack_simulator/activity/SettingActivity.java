package nl.saxion.playground.template.lumberjack_simulator.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lumberjack_simulator.data_storage.JsonHandler;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.GlobalApplication;
import nl.saxion.playground.template.lumberjack_simulator.sound_lib.MusicPlayer;

/**
 * @author Onyebuchi Dinachi, Jokūbas Tumasonis
 */
public class SettingActivity extends AppCompatActivity {
    private boolean removedFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        JsonHandler jsonHandler = new JsonHandler(this);
        jsonHandler.loadConstants();

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeGame();
            }
        });


        Button btnQuitGame = findViewById(R.id.quitButton);
        btnQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGame();
            }
        });

        Button resumeGame = findViewById(R.id.resumeButton);
        resumeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        //mutes all audio in the game
        final android.support.v7.widget.SwitchCompat toggle = findViewById(R.id.soundSwitch);
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean("tgpref", true);  //default is true
        if (tgpref) {
            toggle.setChecked(false);
        } else {
            toggle.setChecked(true);
        }
        toggle.setOnClickListener(new View.OnClickListener() {

            /**
             * Mutes all audio in the game
             * @param v Sound button
             */
            @Override
            public void onClick(View v) {

                if (toggle.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("tgpref", false); // value to store
                    editor.apply();
                    AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    amanager.setStreamMute(AudioManager.STREAM_RING, true);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("tgpref", true); // value to store
                    editor.apply();
                    AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    amanager.setStreamMute(AudioManager.STREAM_RING, false);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                }
            }
        });
    }

    /**
     * Sends you back to the game activity
     */
    public void openActivity() {
        if (removedFile) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        startActivity(new Intent(this, Activity.class));
        finish();
        //onBackPressed();// clean this
    }

    /**
     * Removes save file from the game
     */
    public void removeGame() {
        JsonHandler jsonHandler = new JsonHandler(GlobalApplication.getAppContext());
        jsonHandler.removeData();
        Toast.makeText(getApplicationContext(), "Please relaunch the game to see difference", Toast.LENGTH_LONG).show();
        removedFile = true;
    }

    /**
     * Stops the game
     */
    public void exitGame() {
        MusicPlayer.stop();
        System.exit(0);
    }
}