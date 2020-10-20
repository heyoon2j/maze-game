import java.util.ArrayList;
import java.util.List;

public class MazeGame implements Initializable {
    enum Level {
        Beginner, Intermediate, Expert;
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

    // Board와 Player를 초기화 시킨다.
    @Override
    public void initialize() {
        // Initial board
        switch (level) {
            case Intermediate:
                board = new Board(10);
                break;
            case Expert:
                board = new Board(15);
                break;
            default:
                board = new Board(5);
        }

        // Attach
        player.setMazeGame(this);
        board.setMazeGame(this);

        // Board
        board.initialize();
        this.client = new MazeGameClient(this);
        this.timer = new MazeGameTimer(this);
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