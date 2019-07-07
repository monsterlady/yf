package nl.saxion.playground.template.lumberjack_simulator;

import android.support.annotation.TransitionRes;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lumberjack_simulator.activity.Activity;
import nl.saxion.playground.template.lumberjack_simulator.entities.Background;
import nl.saxion.playground.template.lumberjack_simulator.entities.CoinGenerator;
import nl.saxion.playground.template.lumberjack_simulator.entities.Lumberjack;
import nl.saxion.playground.template.lumberjack_simulator.entities.TreeElement;
import nl.saxion.playground.template.lumberjack_simulator.entities.TreeGenerator;

public class Game extends GameModel {

    private int coinsEarned;

    private Activity gameActivity;

    private CoinGenerator coinGenerator;

    private Lumberjack lumberjack;
    private TreeGenerator treeGenerator;

    private Map<Entity, Boolean> treeChopped;


    public Game(Activity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void start() {
        treeChopped = new HashMap<>();

        Background background = new Background(this);
        addEntity(background);

        treeGenerator = new TreeGenerator(this);
        addEntity(treeGenerator);

        coinGenerator = new CoinGenerator(this);
        addEntity(coinGenerator);

        lumberjack = new Lumberjack(this);
        addEntity(lumberjack);

        treeChopped.put(treeGenerator, false);
        treeChopped.put(coinGenerator, false);
        treeChopped.put(lumberjack, false);

        gameActivity.setPrices(true);


        Log.d("extra", "game Width: " + getWidth() + "f, game Height: " + getHeight() + "f.");
    }

    public void setTreeChopped(boolean chopped) {
        for (Entity entity : treeChopped.keySet()) {
            treeChopped.put(entity, chopped);
        }
    }

    public void setTreeChopped(boolean chopped, Entity entity) {
        treeChopped.put(entity, chopped);
    }

    public void setGameActivity(Activity activity) {
        gameActivity = activity;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned - 1;
        updateTextView();
    }

    public boolean ifTreeChopped(Entity entity) {
        try {
            return treeChopped.get(entity);
        } catch (NullPointerException e) {
            Log.d("extra_info", "Entity non in HashMap");
            e.printStackTrace();
            return false;
        }
    }

    public void updateTextView() {
        coinsEarned++;

        if (gameActivity != null) {
            gameActivity.setTextIndicator(coinsEarned);
        }
    }

    public void setLumberjackDisplayNewAxe() {
        lumberjack.displayNewAxe();
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void addCoinToSpawn() {
        coinGenerator.setNUMBER_OF_COINS(coinGenerator.getNUMBER_OF_COINS() + 1);
    }

    @Override
    public float getWidth() {
        // Virtual screen should be at least 100 wide and 100 high.
        return 100f * actualWidth / Math.min(actualWidth, actualHeight);
    }

    @Override
    public float getHeight() {
        // Virtual screen should be at least 100 wide and 100 high.
        return 100f * actualHeight / Math.min(actualWidth, actualHeight);
    }

    public Activity getGameActivity() {
        return gameActivity;
    }


}
