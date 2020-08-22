public class DirectionVector {
    final int xCoordinate;
    final int yCoordinate;
    Direction direction;

    public DirectionVector(int x, int y, Direction direction) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.direction = direction;
    }
}
