import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BufferedImagePanel {
    private BufferedImage img;
    private Graphics2D g2;
    private String letterSequence;
    private int forwardPixels;

    /*   Here, F means "draw forward",
     − means "turn left 90°", and
     + means "turn right 90°".
      X and Y do not correspond to any drawing action
      and are only used to control the evolution of the curve.*/

    private final char Forward = 'F';
    private final char LeftTurn = '-';
    private final char RightTurn = '+';


    public Icon getIcon() {
        return new ImageIcon(img);
    }

    public BufferedImagePanel(int forwardPixels, String letterSequence) {
        img = new BufferedImage(DragonCurveGrid.WIDTH, (3 * DragonCurveGrid.HEIGHT) / 4, BufferedImage.TYPE_INT_RGB);
        g2 = img.createGraphics();
        this.forwardPixels = forwardPixels;
        this.letterSequence = letterSequence;
        draw();
    }


    public void draw() {
        DirectionVector startingVector = new DirectionVector(99 * DragonCurveGrid.WIDTH / 100, (DragonCurveGrid.HEIGHT) / 3, Direction.SOUTH);
        g2.setColor(Color.white);
        g2.fillRect(0, 0, DragonCurveGrid.WIDTH, DragonCurveGrid.HEIGHT);
        g2.setColor(Color.BLACK);

        for (int i = 0; i < this.letterSequence.length(); i++) {
            switch (this.letterSequence.charAt(i)) {
                case Forward:
                    DirectionVector finalVector = goForward(startingVector, forwardPixels);
                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
                    startingVector = finalVector;
                    break;
                case LeftTurn:
                    startingVector.direction = turnLeft(startingVector.direction);
                    //not sure if the lines above are needed!
//                    finalVector = goForward(startingVector, forwardPixels);
//                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
//                    startingVector.xCoordinate = finalVector.xCoordinate;
//                    startingVector.yCoordinate = finalVector.yCoordinate;
//                    startingVector.direction = finalVector.direction;
                    break;
                case RightTurn:
                    startingVector.direction = turnRight(startingVector.direction);
                    //not sure if the lines above are needed!
//                    finalVector = goForward(startingVector, forwardPixels);
//                    g2.drawLine(startingVector.xCoordinate, startingVector.yCoordinate, finalVector.xCoordinate, finalVector.yCoordinate);
//                    startingVector.xCoordinate = finalVector.xCoordinate;
//                    startingVector.yCoordinate = finalVector.yCoordinate;
//                    startingVector.direction = finalVector.direction;
                    break;
                default:
                    break;
            }
        }

    }

    private DirectionVector goForward(DirectionVector startingVector, int forwardPixels) {

        if (startingVector.direction == Direction.NORTH) {
            return new DirectionVector(startingVector.xCoordinate, startingVector.yCoordinate - forwardPixels, startingVector.direction);
        }
        if (startingVector.direction == Direction.SOUTH) {
            return new DirectionVector(startingVector.xCoordinate, startingVector.yCoordinate + forwardPixels, startingVector.direction);
        }
        if (startingVector.direction == Direction.EAST) {
            return new DirectionVector(startingVector.xCoordinate + forwardPixels, startingVector.yCoordinate, startingVector.direction);
        }
        if (startingVector.direction == Direction.WEST) {
            return new DirectionVector(startingVector.xCoordinate - forwardPixels, startingVector.yCoordinate, startingVector.direction);
        }
        return startingVector;

    }

    private Direction turnRight(Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Direction.EAST;
            case SOUTH:
                return Direction.WEST;
            case EAST:
                return Direction.SOUTH;
            case WEST:
                return Direction.NORTH;
            default:
                return initialDirection;
        }
    }

    private Direction turnLeft(Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Direction.WEST;
            case WEST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.EAST;
            case EAST:
                return Direction.NORTH;
            default:
                return initialDirection;
        }
    }

}
