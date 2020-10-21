package main.game;

public class TimeItemFactory extends ItemFactory {
    @Override
    public Item getItem(String itemName) {
        if (itemName == null) {
            return null;
        } else if (itemName.equalsIgnoreCase("PLUS_TIME")) {
            return new TimePlusItem();
        } else if (itemName.equalsIgnoreCase("MINUS_TIME")) {
            return new TimeMinusItem();
        }
        return null;
    }
}
