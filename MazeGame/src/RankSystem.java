public class RankSystem implements Printable {
    private RankSystem () {}

    public RankSystem getRankSystem() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static RankSystem INSTANCE = new RankSystem();
    }

    @Override
    public void print() {

    }

    public int readFile(String name, String time) {
        return 0;
    }

    public void writeFile(String name, String time, int rank) {

    }
}
