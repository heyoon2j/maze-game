package main;

import main.game.MazeGame;
import main.game.MazeGameTimer;
import main.game.Player;
import main.rank.RankSystem;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("##########################################");
        System.out.println("#####            Maze Game          ######");
        System.out.println("##########################################");
        System.out.println("");
        System.out.println("################## Select ################");
        System.out.println("#####          Game Start : 1        #####");
        System.out.println("#####          Game End   : 2        #####");
        System.out.println("##########################################");
        System.out.println("");

        int selectNum = 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("* Select : ");
            selectNum = scanner.nextInt();
            scanner.nextLine();

            if (selectNum != 1 && selectNum != 2) {
                System.out.println("Input Only 1 or 2");
            } else {
                break;
            }
        }

        if (selectNum == 2) {
            System.out.println("###########       Game End       ##########");
            return;
        }

        System.out.println("###########      Game Start      ##########");
        System.out.println("");

        // Select Player
        System.out.println("* Player Count : 1");
        System.out.print("* Player Name(Length 3) : ");
        String name = scanner.nextLine();
        Player player1 = new Player(name.substring(0,3).toUpperCase());

        // Select Level
        System.out.println("");
        System.out.println("############### Select Level #############");
        System.out.println("#####         Beginner      : 1      #####");
        System.out.println("#####         Intermediate  : 2      #####");
        System.out.println("#####         Expert        : 3      #####");
        System.out.println("##########################################");
        System.out.println("");

        while (true) {
            System.out.print("* Select : ");
            selectNum = scanner.nextInt();
            scanner.nextLine();

            if (selectNum != 1 && selectNum != 2 && selectNum != 3) {
                System.out.println("Input Only 1 or 2 or 3");
            } else {
                break;
            }
        }

        MazeGame.Level level;
        switch (selectNum) {
            case 2:
                level = MazeGame.Level.Intermediate;
                break;
            case 3:
                level = MazeGame.Level.Expert;
                break;
            default:
                level = MazeGame.Level.Beginner;
        }


        MazeGame mazeGame = new MazeGame(level, player1);
        mazeGame.initialize();
        mazeGame.start();
        // Maze Game Over
        String playTime = (player1.getTimeRecord()/1000) + ":" + ((player1.getTimeRecord()/10) % 100);
        System.out.println("Player Name : "+player1.getName() + "/ " + "Record : " + playTime);

        // RankSystem Start
        RankSystem rankSystem = RankSystem.getRankSystem();
        rankSystem.setRankFile(new File("src/main/rank/resources/rankFile.txt"));

        if (player1.getTimeRecord() < MazeGameTimer.FINISH_TIME) {
            int rank = 0;
            if ((rank = rankSystem.readFile(player1.getTimeRecord())) < 11) {
                rankSystem.writeFile(player1.getName(), playTime, rank);
            }
        }
        rankSystem.print();
    }
}
