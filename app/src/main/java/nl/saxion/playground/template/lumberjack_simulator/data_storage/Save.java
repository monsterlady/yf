package nl.saxion.playground.template.lumberjack_simulator.data_storage;

import nl.saxion.playground.template.lumberjack_simulator.store.Price;

/**
 * @author Adomas Aleksandravicius, Michael Cornelisse
 *
 * Class has 1 instance of itself, where we store all information while retriving it from json file
 */

public class Save {
    private int coins;

    private Price[] prices;

    private static Save save;

    static{
        save = new Save();
    }

    public Save() {

    }

    public int getCoins() {
        return coins;
    }

    public static void setSave(Save save) {
        Save.save = save;
    }

    public static Save getInstance(){
        return save;
    }

    public static void setInstance(Save instance){
        save = instance;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setPrices(Price[] prices) {
        this.prices = prices;
    }

    public Price[] getPrices() {
        return prices;
    }
}
