package main.game;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
//        return super.hashCode();
        return x*1000 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        Position pos;
        if (obj instanceof Position) {
            pos = (Position)obj;
        } else {
            return false;
        }

        if (pos.x != this.x || pos.y != this.y) {
            return false;
        } else {
            return true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
