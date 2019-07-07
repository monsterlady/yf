package nl.saxion.playground.template.lumberjack_simulator.data_storage;

import nl.saxion.playground.template.lumberjack_simulator.store.Price;

/**
 * @author Adomas Aleksandravicius, Michael Cornelisse
 */

public class Constants {
    public static int coins = 0;

    public static Price[] prices;

    public static void resetCoins() {
        coins = 0;
    }
}
