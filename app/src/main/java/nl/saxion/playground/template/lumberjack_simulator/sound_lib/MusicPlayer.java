package nl.saxion.playground.template.lumberjack_simulator.sound_lib;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;


import nl.saxion.playground.template.R;

/**
 * Class for the music in LumberJack simulator game
 *
 * @author Michael Cornelisse
 */
public class MusicPlayer {

    private static MediaPlayer mediaPlayer;

    /**
     * Constructor for music player
     *
     * @param context context
     */
    public MusicPlayer(Context context, int musicId) {
        playMusic(context, musicId);

    }

    /**
     *
     * @param context context
     * @param musicId of the song that should be played
     */
    public void playMusic(Context context, int musicId){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context, musicId);
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
        if (musicId == R.raw.piano1){
            mediaPlayer.setLooping(true);
        }
    }

    /**
     * Pauses the music
     */
    public void pauseMusic() {

        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Stops music
     */
    public static void stop() {
        stopMusic();
    }

    /**
     * Logic handler to stop music
     */
    private static void  stopMusic() {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            //mediaPlayer.prepareAsync(); i think we need this but not sure how to use it properly yet.
            mediaPlayer = null;
        }
    }
    //TODO maybe implement AsyncTask
}