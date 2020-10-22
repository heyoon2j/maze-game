package main.game;

import main.Command;

public class MazeGameClient implements Runnable {

    static final boolean[] SIGNAL = new boolean[]{
            false, false, false, false
    };
    // Game End, Print Game Screen, Add main.game.Item, Timer End

    private MazeGame mazeGame;

    MazeGameClient(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
    }

    @Override
    public void run() {
        this.mazeGame.getBoard().print();
        this.mazeGame.getPlayer().play();

        while (true) {
            synchronized (SIGNAL) {
                if (SIGNAL[0]) {
                    this.mazeGame.getBoard().print();
                    this.mazeGame.getPlayer().setTimeRecord(System.currentTimeMillis() - this.mazeGame.getTimer().getStartTime() + this.mazeGame.getPlayer().getTimeRecord());
                    System.out.println("************** GAME END **************");
                    break;
                } else if (SIGNAL[1]) {
                    SIGNAL[1] = false;
                    this.mazeGame.getBoard().print();
                    this.mazeGame.getPlayer().play();
                } else if (SIGNAL[2]) {
                    SIGNAL[2] = false;
                    this.mazeGame.getBoard().print();
                } else if (SIGNAL[3]) {
                    SIGNAL[3] = false;
                    this.mazeGame.getBoard().print();
                    this.mazeGame.getPlayer().setTimeRecord(MazeGameTimer.FINISH_TIME);
                    System.out.println("************** TIME OVER *************");
                    System.out.println("************** GAME END **************");
                    break;
                }
            }
        }

        Command command = new Command() {
            @Override
            public void execute() {
                mazeGame.setStop(true);
            }
        };
        mazeGame.push(command);
    }
}
