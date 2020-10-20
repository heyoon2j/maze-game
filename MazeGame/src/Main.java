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
        System.out.print("* Player Name : ");
        String name = scanner.nextLine();
        Player player1 = new Player(name);

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

        System.out.println("Player Name : "+player1.getName() + "/ " + "Record : " + player1.getTimeRecord());
        //////////////////////////////////////////////////
        /*

        // RankSystem begins
        RankSystem rankSystem = RankSystem.getRankSystem();
        rankSystem.setRankFile(new File("Rank/RankFile.txt"));

        String time = "30:35";
//        String time = player1.getTimeRecord();
        int rank = rankSystem.readFile(time);

//        rankSystem.writeFile(player1.getName(), time, rank);
        rankSystem.writeFile("iop", time, rank);

         */
    }
}
