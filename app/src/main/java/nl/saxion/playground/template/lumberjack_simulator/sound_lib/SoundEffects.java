package nl.saxion.playground.template.lumberjack_simulator.sound_lib;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.Random;

import nl.saxion.playground.template.R;

/**
 * Class for the sound effects in this game
 *
 * @author Michael Cornelisse
 * @since 13-06-2019
 */

public class SoundEffects {
    private static SoundPool soundPool;
    private static int[] chopSounds = {}, coinSounds = {};

    /**
     * Constructor for the in game sound FX
     *
     * @param context baseContext
     */
    public SoundEffects(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        chopSounds = initChopSounds(context);
        coinSounds = initCoinSound(context);
    }

    /**
     * Plays the chopping sound when lumberJack swings his axe
     */
    public void playChopSound() {

        Random randomIndex = new Random();
        soundPool.play(chopSounds[randomIndex.nextInt(chopSounds.length)],
                1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * Initiates the chopSound array
     *
     * @param context baseContext
     * @return array with chopSounds
     */
    private int[] initChopSounds(Context context) {
        int chopSoundOne = soundPool.load(context, R.raw.chopping_wood_01, 1);
        int chopSoundTwo = soundPool.load(context, R.raw.chopping_wood_02, 1);
        int chopSoundThree = soundPool.load(context, R.raw.chopping_wood_03, 1);

        return new int[]{chopSoundOne, chopSoundTwo, chopSoundThree};
    }


    /**
     * Plays the sound when you collect a coin
     */
    public void playCoinSound() {
        Random randomIndex = new Random();
        soundPool.play(coinSounds[randomIndex.nextInt(coinSounds.length)], 1.0f,
                1.0f, 1, 0, 1.0f);

    }

    /**
     * Initiates the coinSound array
     *
     * @param context baseContext
     * @return array with coinSounds
     */
    private int[] initCoinSound(Context context) {

        int coinSoundOne = soundPool.load(context, R.raw.pickup_coin_01, 1);
        int coinSoundTwo = soundPool.load(context, R.raw.pickup_coin_02, 1);
        int coinSoundThree = soundPool.load(context, R.raw.pickup_coin_03, 1);
        int coinSoundFour = soundPool.load(context, R.raw.pickup_coin_04, 1);
        int coinSoundFive = soundPool.load(context, R.raw.pickup_coin_05, 1);

        return new int[]{coinSoundOne, coinSoundTwo, coinSoundThree, coinSoundFour, coinSoundFive};
    }
}

