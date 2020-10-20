import java.util.Timer;
import java.util.TimerTask;

public class MazeGameTimer {
    private MazeGame mazeGame;
    private Timer itemTimer;
    private Timer finishTimer;
    private long recordTime;
    static final long FINISH_TIME = 180000;


    MazeGameTimer(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
        itemTimer = new Timer();
        finishTimer = new Timer();
    }

    public void start(int time) {
        itemTimer.schedule(new TimerTask() {
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
        }, 1000 * 20,1000 * 20);
        // 1000*30 : 30 second


        finishTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                MazeGameClient.SIGNAL[3] = true;
            }
        },FINISH_TIME);
        // 1000*60*1 : 1 minute

        recordTime = System.currentTimeMillis();
    }

    public void stop() {
        finishTimer.cancel();
        itemTimer.cancel();
    }

    public long getRecordTime() {
        return recordTime;
    }
}
