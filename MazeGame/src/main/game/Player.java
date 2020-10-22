package main.game;

import main.Command;
import main.Playable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Playable {
    static final char[] MOVE_ACTION = {'w', 's', 'a', 'd'};

    private String name;
    private long timeRecord;
    private List<Item> itemList;
    private Position prevPos;
    private Position pos;

    private MazeGame mazeGame;  // Observer

    public Player(String name) {
        this.name = name;
        this.itemList = new ArrayList<>();
        this.pos = new Position(1, 1);
        this.prevPos = new Position(1, 1);
        this.timeRecord = 0;
    }

    @Override
    public void play() {
        Command command = new Command() {
            @Override
            public void execute() {
                boolean check = false;
                int moveNum = 0;
                String moveString;

                Scanner scanner = new Scanner(System.in);
                while (!check) {
                    moveString = scanner.nextLine();

                    if (moveString.equals("") || moveString.equals(" "))
                        continue;

                    check = true;
                    if (moveString.charAt(0) == MOVE_ACTION[0]) {
                        moveNum = 0;
                    } else if (moveString.charAt(0) == MOVE_ACTION[1]) {
                        moveNum = 1;
                    } else if (moveString.charAt(0) == MOVE_ACTION[2]) {
                        moveNum = 2;
                    } else if (moveString.charAt(0) == MOVE_ACTION[3]) {
                        moveNum = 3;
                    } else if (moveString.charAt(0) == '!') {
                        return;
                    } else {
                        check = false;
                    }
                }

                mazeGame.getBoard().setPlayerPosition(Player.this, moveNum);

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

    public void setTimeRecord(long timeRecord) {
        Command command = new Command() {
            @Override
            public void execute() {
                Player.this.timeRecord = timeRecord;
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

    public long getTimeRecord() {
        return timeRecord;
    }
}