import java.util.ArrayList;
import java.util.List;

public class Player implements Playable, Runnable {
    private String name;
    private String timeRecord;
    private List<Item> itemList;
    Position pos;
    MazeGame mazeGame;

    Player(String name) {
        this.name = name;
        itemList = new ArrayList<>();
    }

    @Override
    public void play() {

    }

    @Override
    public void run() {

    }

    public void notifyObserver() {

    }

    //@todo addItem, removeItem with List
    public void addItem(Item item) {


    }

    public void removeItem(Item item) {

    }

}
