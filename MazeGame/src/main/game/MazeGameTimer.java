package main.game;

import main.Command;

import java.util.Timer;
import java.util.TimerTask;

public class MazeGameTimer {
    public static final long FINISH_TIME = 180000;
    final long itemGeneratorCycle = 20000;

    private MazeGame mazeGame;
    private Timer itemGeneratorTimer;
    private Timer finishTimer;
    private long startTime;

    MazeGameTimer(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
        this.itemGeneratorTimer = new Timer();
        this.finishTimer = new Timer();
    }

    public void start(int time) {
        itemGeneratorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mazeGame.push(new Command() {
                    @Override
                    public void execute() {
                        mazeGame.getBoard().addItem();
                        MazeGameClient.SIGNAL[2] = true;
                    }
                });
            }
        }, itemGeneratorCycle, itemGeneratorCycle);
        // 1000*30 : 30 second


        finishTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                MazeGameClient.SIGNAL[3] = true;
            }
        }, FINISH_TIME);
        // 1000*60*1 : 1 minute

        startTime = System.currentTimeMillis();
    }

    public void stop() {
        finishTimer.cancel();
        itemGeneratorTimer.cancel();
    }

    public long getStartTime() {
        return startTime;
    }
}
