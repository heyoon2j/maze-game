import java.util.ArrayList;
import java.util.List;

public class Board implements Initializable, Printable {
    static final char[] PLAYER_MARK = {'O'};
    static final char[] WALL = {};

    final char[][] board;
    List<Item> itemList;

    Board(int size) {
        board = new char[size][size];
        itemList = new ArrayList<>();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void print() {

    }

    public void notifyObserver() {

    }

    public void setPlayerPosition(Player cuPlayer, char player_mark) {

    }

    public boolean isPossible(Position pos) {

        return true;
    }

    public boolean isFinished(Player curPlayer) {

        return true;
    }

    public void addItem() {

    }

    public void removeItem() {

    }
}
