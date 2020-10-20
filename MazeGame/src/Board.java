import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Initializable, Printable {
    static final char GOAL = 'X';
    static final int[][] MOVE = {
            {0, -1}, {0, 1}, {-1, 0}, {1, 0}
            // UP, DOWN, LEFT, RIGHT
    };
    static final char[] PLAYER_MARK = {'O'};
    static final char[] BLOCK = {'■', ' ', '@'};    // Wall_BlOCK, BLANK_BLOCK, ITEM_BLCOK
    final int itemCount;    // 0 : 시간 늘리는 아이템, 1 : 시간 줄이는 아이템


    private final char[][] board;
    private Map<Position, Item> itemMap;
    private MazeGame mazeGame;
    private Position goal;

    private List<Node> blankList;
    private boolean[][] visitedPos;

    class Node {
        private int x;
        private int y;
        private Node next;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Board(int size) {
        itemMap = new HashMap<>();
        itemCount = 5;
        board = new char[(size * 2) + 1][(size * 2) + 1];
        visitedPos = new boolean[size][size];
        goal = new Position(0, 0);

        // Generate Random Maze
        blankList = new ArrayList<>();
        List<Node> list = new ArrayList<>();
        randomGenerator(list, 0, 0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                roundBlock((2 * i) + 1, (2 * j) + 1);
            }
        }
        makePass(list);
    }

    private boolean randomGenerator(List<Node> list, int x, int y) {
        // 이미 방문한 적이 있으면 return;
        // 방문한 적이 없으면 True로 변경
        if (visitedPos[y][x]) {
            return false;
        } else {
            visitedPos[y][x] = true;
        }

        boolean block = false;
        boolean[] checkBlock = new boolean[4];
        while (!block) {
            // 0 ~ 3
            int random = (int) (Math.random() * 4);

            // check Out of Index
            checkBlock[random] = true;
            if (!isOutOfIndex(x + MOVE[random][0], y + MOVE[random][1])) {
                continue;
            }

            // Recursive
            if (randomGenerator(list, x + MOVE[random][0], y + MOVE[random][1])) {
                Node node = new Node(x, y);
                node.next = new Node(x + MOVE[random][0], y + MOVE[random][1]);
                list.add(node);
            }

            for (int i = 0; i < checkBlock.length; i++) {
                if (!checkBlock[i]) {
                    block = false;
                    break;
                } else {
                    block = true;
                }
            }
        }
        return true;
    }

    private boolean isOutOfIndex(int x, int y) {
        if (x < 0 || x >= visitedPos.length) {
            return false;
        }
        if (y < 0 || y >= visitedPos.length) {
            return false;
        }
        return true;
    }

    private void roundBlock(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                board[y + i][x + j] = BLOCK[0];
            }
        }
        board[y][x] = BLOCK[1];
    }

    private void makePass(List<Node> list) {
        while (!list.isEmpty()) {
            Node node = list.remove(list.size() - 1);
            blankList.add(node);
            Node next = node.next;
            int x = node.x * 2 + 1;
            int y = node.y * 2 + 1;
            int nextX = next.x * 2 + 1;
            int nextY = next.y * 2 + 1;

            if (x == nextX) {
                // y(세로)의 사이에 통로가 생겨야 된다.
                int temp = (nextY - y > 0) ? y + 1 : y - 1;
                board[temp][x] = ' ';
            } else {
                // x(가로) 축 사이에 통로가 생겨야 된다.
                int temp = (nextX - x > 0) ? x + 1 : x - 1;
                board[y][temp] = ' ';
            }
        }
    }

    @Override
    public void initialize() {
        // Select Start and Finish Line
        mazeGame.getPlayer().setPos(new Position(1, 1));
        mazeGame.getPlayer().setPrevPos(new Position(1, 1));
        goal.setX(board.length - 2);
        goal.setY(board.length - 2);
        board[1][1] = PLAYER_MARK[0];
        board[goal.getY()][goal.getX()] = GOAL;
    }

    @Override
    public void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }
    }

    public void setPlayerPosition(Player curPlayer, char position) {
        int i = 0;
        if ((i = isPossible(curPlayer, position)) != -1) {
            // MOVE = {0, -1}, {0, 1}, {-1, 0} ,{1, 0}
            // UP, DOWN, LEFT, RIGHT
            curPlayer.getPrevPos().setX(curPlayer.getPos().getX());
            curPlayer.getPrevPos().setY(curPlayer.getPos().getY());
            curPlayer.getPos().setX(curPlayer.getPos().getX() + MOVE[i][0]);
            curPlayer.getPos().setY(curPlayer.getPos().getY() + MOVE[i][1]);
            board[mazeGame.getPlayer().getPrevPos().getY()][mazeGame.getPlayer().getPrevPos().getX()] = BLOCK[1];
            board[mazeGame.getPlayer().getPos().getY()][mazeGame.getPlayer().getPos().getX()] = PLAYER_MARK[0];
        }
    }

    public int isPossible(Player curPlayer, char position) {
        // MOVE_ACTION = {'w', 's', 'a', 'd'}
        for (int i = 0; i < 4; i++) {
            if (position == Player.MOVE_ACTION[i]) {
                int x = curPlayer.getPos().getX() + MOVE[i][0];
                int y = curPlayer.getPos().getY() + MOVE[i][1];
                char checkChar = this.board[y][x];
                if (checkChar == GOAL || checkChar == BLOCK[1]) {
                    return i;
                    // return i means it can move
                } else if (checkChar == BLOCK[2]) {
//                    curPlayer.addItem(itemMap.get(new Position(x, y)));
                    System.out.println();
                    itemMap.get(new Position(x, y)).use(curPlayer);
                    removeItem(x, y);
                    return i;
                }
                break;
            }
        }
        return -1;
    }

    public boolean isFinished(Player curPlayer) {
        return curPlayer.getPos().getX() == goal.getX() && curPlayer.getPos().getY() == goal.getY();
    }

    public void addItem() {
        int random = (int) (Math.random() * blankList.size());
        Position pos = new Position(blankList.get(random).x * 2 + 1, blankList.get(random).y * 2 + 1);

        if (board[pos.getY()][pos.getX()] == GOAL || board[pos.getY()][pos.getX()] == PLAYER_MARK[0]) {
            return;
        }

        if (itemMap.get(pos) == null) {
            ItemFactory itemFactory = new TimeItemFactory();
            Item item;
            random = (int) Math.random() * 10;
            if (random >= 0 && random < 6) {
                item = itemFactory.getItem("MINUS_TIME");
//                item = itemFactory.getItem("PLUS_TIME");
            } else {
                item = itemFactory.getItem("MINUS_TIME");
            }
            itemMap.put(pos, item);
            board[pos.getY()][pos.getX()] = BLOCK[2];
        }
    }

    public void removeItem(int x, int y) {
        Position pos = new Position(x, y);
        if (itemMap.get(pos) != null) {
            itemMap.remove(pos);
        }
    }

    public void setMazeGame(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
    }
}
