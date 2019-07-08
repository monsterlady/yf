package nl.saxion.playground.template.lumberjack_simulator.entities;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lumberjack_simulator.Game;
import nl.saxion.playground.template.lumberjack_simulator.data_storage.Constants;

/**
 * A class, which has List of tree blocks and manages them
 * @author Mark Kravchuk
 */

public class TreeGenerator extends Entity {

    private List<TreeElement> logs;

    private boolean addNewLog;
    //Other entities use this values to adjust theirs position according to tree
    static float TREE_Y_AXIS_FINISH;
    static float TREE_X_AXIS;

    private static final byte LOGS_AMOUNT = 6;

    private Game game;

    public TreeGenerator(Game game) {
        this.game = game;
        logs = new ArrayList<>();
        TREE_X_AXIS = game.getWidth() * 0.35f;
        TREE_Y_AXIS_FINISH = game.getHeight() * 0.55f;
        init();
    }

    /**
     * We check if lumberjack chopped tree,
     * so tree can destroy 1 block and make all the rest fall down
     */

    @Override
    public void tick() {
        if (game.ifTreeChopped(this)) {

            /**
             * Explanation for Buchi
             * Check if tree block to cut is special
             * If is set isSpecial true in Constants
             * */
            if(logs.get(0).getSpecial()){
                Constants.setIsSpecialtreecut(true);
            } else {
                Constants.setIsSpecialtreecut(false);
            }
            logs.remove(0);
            game.setTreeChopped(false, this);
            addNewLog = true;


        }
        if (addNewLog) {
            TreeThread thread = new TreeThread();
            addNewLog = false;
            thread.start();
        }
    }

    @Override
    public void draw(GameView gv) {
        for (int i = 0; i < logs.size(); i++) {
           logs.get(i).draw(gv);
        }

        TreeElement bottomBlock = new TreeElement();
        bottomBlock.setBottom(true);
        bottomBlock.position.x = TREE_X_AXIS;
        bottomBlock.position.y = TREE_Y_AXIS_FINISH + 20f;
        bottomBlock.draw(gv);
    }

    private void init(){
        float y = TREE_Y_AXIS_FINISH;

        for (int i = 0; i <= LOGS_AMOUNT; i++) {
            TreeElement log = new TreeElement();

            float logSize = 20f;

            log.position.x = TREE_X_AXIS;
            log.position.y = y;
            logs.add(log);

            Log.i("extra_info", "Coordinates " + i + ") X:" + TREE_X_AXIS + ", Y: " + y);
            y -= logSize;
        }

        Constants.treeElementArrayList = logs;
    }

    /**
     * Thread was created to make blocks of tree falling down one by one slowly
     * making this process visible for user in animated way.
     */

    private class TreeThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < LOGS_AMOUNT; i++) {
                for (int j = 0; j < 5; j++) {
                    logs.get(i).position.y += 4f;
                    customsleep(30);
                }
                customsleep(60);
            }

            TreeElement element = new TreeElement();
            element.position.x = TREE_X_AXIS;
            element.position.y = logs.get(logs.size() - 1).position.y - 20f;

            logs.add(element);
        }

        private void customsleep(int millis) {
            try {
                sleep(millis);
            } catch (InterruptedException e) {
                Log.d("extra_info", "Exception with sleep in thread");
            }
        }
    }
}