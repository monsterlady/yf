package nl.saxion.playground.template.lumberjack_simulator.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lumberjack_simulator.Game;
import nl.saxion.playground.template.lumberjack_simulator.local_lib.Vector;

/**
 * @author Mark Kravchuk
 */

public class TreeElement extends Entity {

    private Bitmap bitmap;
    private boolean bottom;
    private boolean isSpecial = false;
    private final static double chanceofSpeacil = 0.7;

    Vector position;

    private float width;
    private float height;

    public TreeElement() {
        position = new Vector();
        width = 20f;
        height = 20f;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean getSpecial() {
        return isSpecial;
    }

    @Override
    public void draw(GameView gv) {
        double random = Math.random();
        if (bitmap == null) {

            /**
             * Explanation for Buchi
             * Created a constants named chanceofSpecial
             * Statements below means if a random number is bigger than the constants created above
             * then create a special tree block
             * otherwise create regular tree block.
             * */
            if(random < chanceofSpeacil || bottom) {
                bitmap = BitmapFactory.decodeResource(gv.getResources(), R.drawable.wood_block_medium);
            } else {
                isSpecial = true;
                bitmap = BitmapFactory.decodeResource(gv.getResources(), R.drawable.wood_block_special);
            }
        }
        gv.drawBitmap(bitmap, position.x, position.y, width, height);
    }
}
