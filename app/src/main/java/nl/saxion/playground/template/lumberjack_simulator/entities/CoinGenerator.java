package nl.saxion.playground.template.lumberjack_simulator.entities;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lumberjack_simulator.Game;
import nl.saxion.playground.template.lumberjack_simulator.data_storage.Constants;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.GlobalApplication;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.Vector;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lumberjack_simulator.sound_lib.SoundEffects;

/**
 * @author Mark Kravchuk
 * Class, which maintains a List of coins
 */

public class CoinGenerator extends Entity {

    private Game game;
    private SoundEffects soundEffects;

    private int NUMBER_OF_COINS = 1;
    private int frameForCoin = 4;
    private int tickRate;
    private int holderofCoin;

    private List<CoinElement> coins;

    public CoinGenerator(Game game) {
        this.game = game;
        coins = new ArrayList<>();
        Context context = GlobalApplication.getAppContext();
        soundEffects = new SoundEffects(context);

    }


    @Override
    public void tick() {
        //check if lumberjack chopped the tree
            if (game.ifTreeChopped(this)) {
                /**
                 * Explanation for Buchi
                 * There is a boolean in Constants named isSpecailtreecut, which is used to determine whether current cut tree block is special
                 * If it is, then number of coin gonna become double of that of previous
                 * Fot instance: 1 coin will come out when normal tree block was cut
                 * if it is special one, then 2 coins will come out
                 * */
                if(Constants.isSpecialtreecut){
                    holderofCoin = NUMBER_OF_COINS * 2;
                    for (int i = 0; i < holderofCoin; i++) {
                        CoinElement element = new CoinElement();
                        element.setPosition(TreeGenerator.TREE_X_AXIS, 100f);

                        float directionX = generateNumber(-3, element.MIN_X_SPEED);
                        float directionY = generateNumber(-7f, 4f);

                        element.setDirection(new Vector(directionX, directionY));

                        coins.add(element);
                    }
                } else {

                    for (int i = 0; i < NUMBER_OF_COINS; i++) {
                        CoinElement element = new CoinElement();
                        element.setPosition(TreeGenerator.TREE_X_AXIS, 100f);

                        float directionX = generateNumber(-3, element.MIN_X_SPEED);
                        float directionY = generateNumber(-7f, 4f);

                        element.setDirection(new Vector(directionX, directionY));

                        coins.add(element);
                    }
                }
                game.setTreeChopped(false, this);
            }


        if(tickRate % 30 == 0) {
            frameForCoin++;
            if(frameForCoin > 7){
                frameForCoin = 0;
            }
        }


        if (tickRate < Integer.MAX_VALUE - 5) tickRate++;
        else {
            tickRate = 0;
        }

        for (CoinElement element : coins){
            element.tick();
        }
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        //here if statement checks if user pressed on any of coins
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            for (int i = coins.size() - 1; i > -1; i--) {
                if (touch.x > coins.get(i).getPosition().x
                        && touch.x < coins.get(i).getPosition().x + CoinElement.WIDTH
                        && touch.y > coins.get(i).getPosition().y
                        && touch.y < coins.get(i).getPosition().y + CoinElement.HEIGHT) {
                    Log.d("extra_info", "Silver removed");
                    soundEffects.playCoinSound();
                    coins.remove(i);
                    game.updateTextView();
                }
            }
        }else if(event.getAction() == MotionEvent.ACTION_DOWN){
            for (int i = coins.size() - 1; i > -1; i--) {
                // teacher: Can you simplify the conditions inside if.
                if (touch.x > coins.get(i).getPosition().x && touch.x < coins.get(i).getPosition().x + CoinElement.WIDTH
                        && touch.y > coins.get(i).getPosition().y && touch.y < coins.get(i).getPosition().y + CoinElement.HEIGHT) {
                    Log.d("extra_info", "Silver removed");
                    soundEffects.playCoinSound();
                    coins.remove(i);
                    game.updateTextView();
                }
            }
        }
    }

    @Override
    public void draw(GameView gv) {
        // Teacher: Looping for on draw can be slower. I do not know exactly what you are trying to do here.
        for (CoinElement element : coins) {
            element.draw(gv,frameForCoin);
        }
    }

    private float generateNumber(float from, float till){
        float difference = Math.abs(till - from);
        return (float) (Math.random() * difference) + from;
    }

    public void setNUMBER_OF_COINS(int NUMBER_OF_COINS) {
        this.NUMBER_OF_COINS = NUMBER_OF_COINS;
    }

    public int getNUMBER_OF_COINS() {
        return NUMBER_OF_COINS;
    }
}
