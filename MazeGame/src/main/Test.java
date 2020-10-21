package main;

import main.rank.RankSystem;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        RankSystem rankSystem = RankSystem.getRankSystem();

        rankSystem.setRankFile(new File("src/main/rank/resources/rankFile.txt"));

//        File a = new File("src/main/rank/resources/rankFile.txt");

//        System.out.println(a.getAbsolutePath());
    }
}
