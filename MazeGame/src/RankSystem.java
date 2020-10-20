import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;


public class RankSystem implements Printable {
    private File rankFile;

    private RankSystem () {}

    public static RankSystem getRankSystem() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static RankSystem INSTANCE = new RankSystem();
    }

    @Override
    public void print() {

    }

    public int readFile(String time) {

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

                    if(parse[1].equals("등수")) {
                        continue;
                    }

                    int rank = Integer.parseInt(parse[1]);

                    String compareTime = parse[2];

                    if(time.compareTo(compareTime) <= 0) {
                        return rank;
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void writeFile(String name, String time, int rank) {

    }

    public void setRankFile(File rankFile) {
        this.rankFile = rankFile;
    }
}
