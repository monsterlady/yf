package nl.saxion.playground.template.lumberjack_simulator.data_storage;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.playground.template.lumberjack_simulator.entities.TreeElement;
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

    public static List<TreeElement> treeElementArrayList = new ArrayList<>();

    public static boolean isSpecialtreecut = false;

    public static void setIsSpecialtreecut(boolean isSpecialtreecut) {
        Constants.isSpecialtreecut = isSpecialtreecut;
    }
}
