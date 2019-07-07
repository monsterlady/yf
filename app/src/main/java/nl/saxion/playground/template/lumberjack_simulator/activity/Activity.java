package nl.saxion.playground.template.lumberjack_simulator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lumberjack_simulator.Game;
import nl.saxion.playground.template.lumberjack_simulator.data_storage.JsonHandler;
import nl.saxion.playground.template.lumberjack_simulator.data_storage.Save;
import nl.saxion.playground.template.lumberjack_simulator.store.BuyView;

public class Activity extends AppCompatActivity {

    private Game game;
    private GameView gameView;
    private TextView coinIndicator;
    BuyView buyView;
    private JsonHandler jsonHandler;

    private static int buyViewOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonHandler = new JsonHandler(getApplicationContext());

        setContentView(R.layout.activity_);
        //loading data from file
        jsonHandler = new JsonHandler(getApplicationContext());
        jsonHandler.loadConstants();

        gameView = findViewById(R.id.lumberView33);

        buyView = findViewById(R.id.buy_view);

        coinIndicator = findViewById(R.id.coins);

        game = new Game(this);
        Save save = Save.getInstance();
        int coins = save.getCoins();
        game.setCoinsEarned(coins);


        buyView.transparent("invisible");
        buyView.setGame(game);

        game.setGameActivity(this);
        gameView.setGame(game);
    }

    public void setPrices(boolean set){
        if(set) buyView.setPrices(Save.getInstance().getPrices());
        else buyView.setPrices(null);
    }

    public void setTextIndicator(int coins){
        coinIndicator.setText("Coins: " + coins);
    }

    public void onClick(View v) {
        if(buyViewOpen % 2 == 0){
            buyView.transparent("visible");
        } else {
            buyView.transparent("gone");
        }
        buyViewOpen++;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setGame(game);
    }

    @Override
    protected void onPause() {
        save();
        gameView.setGame(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        save();
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }


    private void save(){
        Save.getInstance().setCoins(game.getCoinsEarned());
        Save.getInstance().setPrices(buyView.getPrices());
        jsonHandler.saveConstants();
    }
}
