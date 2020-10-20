import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Playable {
    static final char[] MOVE_ACTION = {'w', 's', 'a', 'd'};

    private String name;
    private String timeRecord;
    private List<Item> itemList;
    private Position prevPos;
    private Position pos;
    private MazeGame mazeGame;

    Player(String name) {
        this.name = name;
        this.itemList = new ArrayList<>();
        this.pos = new Position(1, 1);
        this.prevPos = new Position(1, 1);
        this.timeRecord = "0";
    }

    @Override
    public void play() {
        Command command = new Command() {
            @Override
            public void execute() {
                Scanner scanner = new Scanner(System.in);

                boolean check = false;
                String moveString;
                char move = 0;

                while (!check) {
                    moveString = scanner.nextLine();
                    if (moveString.equals("") || moveString.equals(" "))
                        continue;
                    check = true;
                    switch (moveString.charAt(0)) {
                        case 'w':
                            move = Player.MOVE_ACTION[0];
                            break;
                        case 's':
                            move = Player.MOVE_ACTION[1];
                            break;
                        case 'a':
                            move = Player.MOVE_ACTION[2];
                            break;
                        case 'd':
                            move = Player.MOVE_ACTION[3];
                            break;
                        case '!':
                            return;
                        default:
                            check = false;
                    }
                }

                mazeGame.getBoard().setPlayerPosition(Player.this, move);

                if (mazeGame.getBoard().isFinished(Player.this)) {
                    synchronized (MazeGameClient.SIGNAL) {
                        MazeGameClient.SIGNAL[0] = true;
                    }
                } else {
                    synchronized (MazeGameClient.SIGNAL) {
                        MazeGameClient.SIGNAL[1] = true;
                    }
                }
            }
        };
        this.mazeGame.push(command);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Position prevPos) {
        this.prevPos = prevPos;
    }

    public void setMazeGame(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
    }

    public String getName() {
        return name;
    }

    public String getTimeRecord() {
        return timeRecord;
    }
}