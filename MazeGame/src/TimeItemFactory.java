public class TimeItemFactory extends ItemFactory {
    @Override
    public Item getItem(String itemName) {
        if (itemName == null) {
            return null;
        } else if (itemName.equalsIgnoreCase("PLUS_TIME")) {
            return new TimeItem(TimeItem.Item.PLUS_TIME);
        } else if (itemName.equalsIgnoreCase("MINUS_TIME")) {
            return new TimeItem(TimeItem.Item.MINUS_TIME);
        }
        return null;
    }
}
