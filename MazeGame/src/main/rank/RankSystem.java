package main.rank;

import main.Printable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class RankSystem implements Printable {
    private File rankFile;

    // Singleton LazyHolder
    private RankSystem () {}

    public static RankSystem getRankSystem() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static RankSystem INSTANCE = new RankSystem();
    }

    @Override
    public void print() {
        try (FileReader fr = new FileReader(rankFile); BufferedReader br = new BufferedReader(fr)){
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readFile(long time) {
        try (FileInputStream fis = new FileInputStream(this.rankFile);
             InputStreamReader isr = new InputStreamReader(fis)) {

            char[] cbuf = new char[37];
            int read = 0;
            int index = 0;

            while((read = isr.read()) != -1) {
                if(read != '\n') {
                    cbuf[index] = (char)read;;
                    index++;
                } else {
                    index = 0;

                    String[] parse = String.valueOf(cbuf)
                            .replace(" ", "")
                            .split("\\|");

                    // First Line Erase
                    if(parse[1].equals("등수")) {
                        continue;
                    }
                    // 30:11
                    // 30110
                    long comparedTime = Long.parseLong(parse[2].replace(":","")) * 10;

                    if(time < comparedTime) {
                        return Integer.parseInt(parse[1]);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void writeFile(String name, String time, int rank) {

        List<String[]> list = new ArrayList<>();

        try (FileReader fr = new FileReader(rankFile); BufferedReader br = new BufferedReader(fr);){

            String line = null;
            boolean change = false;
            String tempName = null;
            String tempTime = null;

            // 리스트 저장
            while ((line = br.readLine()) != null) {
                String[] parseLine = line.replace(" ", "")
                                        .split("\\|");

                if (parseLine[1].equals("등수")) {
                    continue;
                }

                // 여기서 부터 바꿔치기 시작
                String[] stringArray = new String[3];
                stringArray[0] = parseLine[1];
                if (Integer.parseInt(parseLine[1]) == rank) {
                    change = true;
                    tempTime = time;//(time/1000) + ":" + ((time/10) % 100);
                    tempName = name;
                }

                if (change) {
                    stringArray[1] = tempTime;
                    stringArray[2] = tempName;

                    tempTime = parseLine[2];
                    tempName = parseLine[3];
                } else {
                    stringArray[1] = parseLine[2];
                    stringArray[2] = parseLine[3];
                }
                list.add(stringArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start Writing
        try (FileWriter fw = new FileWriter(rankFile); BufferedWriter bw = new BufferedWriter(fw)) {
            // 쓰기
            bw.write("|  등 수  |  클리어 시간  |  이 름  |\n");
            while (!list.isEmpty()) {
                String[] stringArray = list.remove(0);
                bw.write("|    " + stringArray[0] + "   |    " + stringArray[1] + "     |   " + stringArray[2] + "  |\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRankFile(File rankFile) {
        this.rankFile = rankFile;
    }
}
