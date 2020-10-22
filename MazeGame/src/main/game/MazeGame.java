package main.game;

import main.Command;
import main.Initializable;

import java.util.ArrayList;
import java.util.List;

public class MazeGame implements Initializable {
    public enum Level {
        Beginner(5), Intermediate(10), Expert(15);

        private int size;

        private Level(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    private final List<Command> command = new ArrayList<>();
    private boolean stop;
    private Board board;
    private Level level;
    private Player player;
    private MazeGameClient client;
    private MazeGameTimer timer;

    public MazeGame(Level level, Player player) {
        this.level = level;
        this.player = player;
    }

    // Board 초기화 시킨다.
    @Override
    public void initialize() {
        // Initial board
        switch (level) {
            case Intermediate -> board = new Board(Level.Intermediate.getSize());
            case Expert -> board = new Board(Level.Expert.getSize());
            default -> board = new Board(Level.Beginner.getSize());
        }

        // Attach
        this.player.setMazeGame(this);
        this.board.setMazeGame(this);
        this.client = new MazeGameClient(this);
        this.timer = new MazeGameTimer(this);

        board.initialize();
    }

    public void start() {

        Thread clientThread = new Thread(this.client);
        clientThread.start();

        this.timer.start(1000);

        while (!this.stop) {
            synchronized (command) {
               if (!command.isEmpty()) {
                    this.action();
                }
            }
        }
        this.timer.stop();
    }

    public void push(Command command) {
        synchronized (this.command) {
            this.command.add(command);
        }
    }

    public void action() {
            if (!this.command.isEmpty()) {
                Command command = this.command.remove(0);
                Thread playThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        command.execute();
                    }
                });
                playThread.start();
            }
    }

    public void pop() {
        synchronized (this.command) {
            if (!this.command.isEmpty()) {
                this.command.remove(this.command.size() - 1);
            }
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Command> getCommand() {
        return command;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public MazeGameClient getClient() {
        return client;
    }

    public MazeGameTimer getTimer() {
        return timer;
    }
}