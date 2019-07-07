package nl.saxion.playground.template.lumberjack_simulator.entities;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lumberjack_simulator.Game;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.GlobalApplication;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.Vector;
import nl.saxion.playground.template.lumberjack_simulator.sound_lib.SoundEffects;

/**
 * @author Mark Kravchuk
 * Main character class
 */

public class Lumberjack extends Entity {

    private Context context;
    private SoundEffects soundEffects;
    private final float width = 20f;
    private final float height = 35f;

    private final Vector[] positions;

    private static final double TIME_TO_DO_ALL_ANIMATION = 70;

    private int tickRate;
    private int lastTouched;

    private boolean touched;

    private int touchesToDestroy;
    private int leftTouchesToDestroy;

    private Bitmap[][] bitmap;

    private int indexCurrentAxe;

    private boolean instantiated;

    private Game game;

    private int frame;

    //margins
    private float left;
    private float top;

    private boolean playedAlready;

    public Lumberjack(Game game) {
        this.game = game;
        bitmap = new Bitmap[4][4];
        //10 times to click on Lumberjack to chop 1 tree block
        touchesToDestroy = 10;
        //left margin
        left = 0.6f * game.getWidth();
        //top margin
        top = TreeGenerator.TREE_Y_AXIS_FINISH + 5f;

        leftTouchesToDestroy = touchesToDestroy;

        //touch zones top left & bottom right
        positions = new Vector[2];

        float extraZone = 15f;

        positions[0] = new Vector(left - extraZone, top - extraZone);
        positions[1] = new Vector(left + width + extraZone, top + height + extraZone);

        context = GlobalApplication.getAppContext();
        soundEffects = new SoundEffects(context);
    }

    @Override
    public void tick() {
        if (touched) {
            //here we choose which frame to show
            int chooser = tickRate - lastTouched;
            if (chooser < TIME_TO_DO_ALL_ANIMATION * 0.2) {
                frame = 0;
            } else if (chooser < TIME_TO_DO_ALL_ANIMATION * 0.45) {
                frame = 1;
            } else if (chooser < TIME_TO_DO_ALL_ANIMATION * 0.7) {
                frame = 2;
            } else if (chooser < TIME_TO_DO_ALL_ANIMATION) {
                frame = 3;
                //make sound of chopping once in animation time
                if(!playedAlready) {
                    soundEffects.playChopSound();
                    playedAlready = true;
                }
            } else {
                leftTouchesToDestroy--;
                if (leftTouchesToDestroy < 0) {
                    //Give for another entities that lumberjack chopped a block
                    game.setTreeChopped(true);
                    leftTouchesToDestroy = touchesToDestroy; //assign number of chops back(10,7,5,3)
                }
                frame = 0;
                touched = false;
                //next chop music to play
                playedAlready = false;
            }
        }

        if (tickRate < Integer.MAX_VALUE - 100) tickRate++;
        else {
            tickRate = 0;
            lastTouched = (int) TIME_TO_DO_ALL_ANIMATION;
            lastTouched = lastTouched * -1;
        }
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        //touch should be bigger than top left coordinate (positions[0])
        //and less than bottom right coordinate(positions[1])
        if (touch.x > positions[0].x && touch.x < positions[1].x
                && touch.y > positions[0].y && touch.y < positions[1].y) {
            //check if touch was made after animation finishes
            if (tickRate - lastTouched > TIME_TO_DO_ALL_ANIMATION) {
                if (!touched) {
                    lastTouched = tickRate;
                    touched = true;
                    Log.i("events", tickRate + ") Touched! Button works");
                } else {
                    Log.i("events", "Button hasn`t finished everything to do (boolean touched=false).");
                }
            } else {
                Log.i("events", tickRate + ") Button is banned and was not procedured");
            }
        } else {
            Log.d("extra_info","Button was not addressed to lumberjack");
        }
    }

    @Override
    public void draw(GameView gv) {

        //check if bitmap is not null
        if (!instantiated) {
                bitmap = instantiatePictures(gv);
                instantiated = true;
        }

        //because our timber images have different size,
        //we try to make them look same
        //and display in animated order

        float extraWidth = width + 35f;

        float extraLeft = left - 18f;

        showBitmap(gv,extraLeft,extraWidth);
    }

    public void displayNewAxe(){
        if(indexCurrentAxe < 3) indexCurrentAxe++;

        //setting up amount user has
        // to click to chop 1 block tree

        //list of amountOfChops
        //wooden 10
        //iron 7
        //golden 5
        //diamond 3

        switch (touchesToDestroy) {
            case 10:
                //iron
                touchesToDestroy = 7;
                break;
            case 7:
                //gold
                touchesToDestroy = 5;
                break;
            case 5:{
                //diamond
                touchesToDestroy = 3;
                break;
            }
        }

    }

    private void showBitmap(GameView gv, float extraLeft,float extraWidth){
        switch (frame) {
            case 0:
                gv.drawBitmap(bitmap[0][indexCurrentAxe], left, top, width, height, 0);
                break;
            case 1:
                gv.drawBitmap(bitmap[1][indexCurrentAxe], extraLeft - 1f, top, extraWidth, height);
                break;
            case 2:
                gv.drawBitmap(bitmap[2][indexCurrentAxe], extraLeft - 2f, top, extraWidth, height);
                break;
            case 3:
                gv.drawBitmap(bitmap[3][indexCurrentAxe], extraLeft - 3f, top, extraWidth, height);
                break;
        }
    }

    private Bitmap[][] instantiatePictures(GameView gv) {
        Bitmap[][] bitmap = new Bitmap[4][4];

        //setting up wooden axe with Lumberjack

        bitmap[0][0] = gv.getBitmapFromResource(R.drawable.timberman);
        bitmap[1][0] = gv.getBitmapFromResource(R.drawable.timberman_wood1);
        bitmap[2][0] = gv.getBitmapFromResource(R.drawable.timberman_wood2);
        bitmap[3][0] = gv.getBitmapFromResource(R.drawable.timberman_wood3);

        //Now setting up iron axe with Lumberjack

        bitmap[0][1] = gv.getBitmapFromResource(R.drawable.timberman);
        bitmap[1][1] = gv.getBitmapFromResource(R.drawable.timberman_iron1);
        bitmap[2][1] = gv.getBitmapFromResource(R.drawable.timberman_iron2);
        bitmap[3][1] = gv.getBitmapFromResource(R.drawable.timberman_iron3);

        //And setting up golden axe with Lumberjack

        bitmap[0][2] = gv.getBitmapFromResource(R.drawable.timberman);
        bitmap[1][2] = gv.getBitmapFromResource(R.drawable.timberman_gold1);
        bitmap[2][2] = gv.getBitmapFromResource(R.drawable.timberman_gold2);
        bitmap[3][2] = gv.getBitmapFromResource(R.drawable.timberman_gold3);

        //Finishing  with diamond axe with Lumberjack

        bitmap[0][3] = gv.getBitmapFromResource(R.drawable.timberman);
        bitmap[1][3] = gv.getBitmapFromResource(R.drawable.timberman_diamond1);
        bitmap[2][3] = gv.getBitmapFromResource(R.drawable.timberman_diamond2);
        bitmap[3][3] = gv.getBitmapFromResource(R.drawable.timberman_diamond3);

        return bitmap;
    }


    public void setContext(Context context) {
        this.context = context;
        soundEffects = new SoundEffects(context);
    }
}

