package day12;

import util.Reader;

import java.io.FileNotFoundException;

/**
 * Puzzle 12 from the Advent of Code calendar.
 * Rain Risk.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day12 {

    /**
     * The instruction class presents one navigation instruction generated by the navigation computer.
     */
    public static class Instruction {

        private final char action;
        private final int value;

        public Instruction(String instruction) {
            this.action = instruction.charAt(0);
            this.value = Integer.parseInt(instruction.substring(1));
        }

        public char getAction() {
            return action;
        }

        public int getValue() {
            return value;
        }
    }

    private final Instruction[] instructions;

    /**
     * East is + and west is -.
     */
    private int shipX;

    /**
     * Relative vertical position between the waypoint and the ship.
     */
    private int wayPointX;

    /**
     * North is + and south is -.
     */
    private int shipY;

    /**
     * Relative horizontal position between the waypoint and the ship.
     */
    private int wayPointY;

    public static final char EAST = 'E';
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char WEST = 'W';
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';
    public static final char FORWARD = 'F';

    /**
     * Read the input file and fill the list with instructions.
     * @ensures shipX = 0; shipY = 0; wayPointX = 0; wayPointY = 0;
     * @throws FileNotFoundException when the input file is non existent.
     */
    public Day12() throws FileNotFoundException {
        String[] input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day12/input.txt");
        instructions = new Instruction[input.length];
        for (int i = 0; i < input.length; i++) {
            instructions[i] = new Instruction(input[i]);
        }

        shipX = 0;
        shipY = 0;
        wayPointX = 10; // Waypoint starts 10 units east from the ship.
        wayPointY = 1;// Waypoint starts 1 unit north from the ship.
    }

    /**
     * Part A
     * Moves the ship according to the instructions provided by the navigational computer.
     *
     * NORTH = Move the ship <code>value</code> units towards the north.
     * SOUTH = Move the ship <code>value</code> units towards the south.
     * EAST = Move the ship <code>value</code> units towards the east.
     * WEST = Move the ship <code>value</code> units towards the west.
     * LEFT = Turn the ship <code>value</code> degrees to the left.
     * RIGHT = Turn the ship <code>value</code> degrees to the right.
     * FORWARD = Move the ship <code>value</code> units towards its current direction.
     */
    public void findDestination() {
        // Current direction in degrees, north is 0.
        int currentDirection = 90;

        for(Instruction instruction : instructions) {
            char action = instruction.getAction();
            int value = instruction.getValue();

            switch (action) {
                case NORTH:
                    shipX += value;
                    break;
                case SOUTH:
                    shipX -= value;
                    break;
                case EAST:
                    shipY += value;
                    break;
                case WEST:
                    shipY -= value;
                    break;
                case LEFT:
                    currentDirection = (currentDirection - value) % 360;
                    if(currentDirection < 0) {
                        currentDirection = currentDirection + 360;
                    }
                    break;
                case RIGHT:
                    currentDirection = (currentDirection + value) % 360;
                    if(currentDirection < 0) {
                        currentDirection = currentDirection + 360;
                    }
                    break;
                case FORWARD:
                    switch (currentDirection) {
                        case 0:
                            shipX += value;
                            break;
                        case 90:
                            shipY += value;
                            break;
                        case 180:
                            shipX -= value;
                            break;
                        case 270:
                            shipY -= value;
                            break;
                        default:
                            System.err.println("Invalid direction! " + currentDirection);
                            System.exit(0);
                            break;
                    }
                    break;
                default:
                    System.err.println("Invalid action! " + action);
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Part B
     * Moves the ship according to the instructions provided by the navigational computer.
     *
     * NORTH = Move the waypoint <code>value</code> units towards the north.
     * SOUTH = Move the waypoint <code>value</code> units towards the south.
     * EAST = Move the waypoint <code>value</code> units towards the east.
     * WEST = Move the waypoint <code>value</code> units towards the west.
     * LEFT = Moves the waypoint <code>value</code> degrees counter-clockwise around the ship.
     * RIGHT = Moves the waypoint <code>value</code> degrees clockwise around the ship.
     * FORWARD = Move the ship <code>value</code> times towards the waypoint.
     */
    public void findDestination2() {
        for(Instruction instruction : instructions) {
            char action = instruction.getAction();
            int value = instruction.getValue();

            switch (action) {
                case NORTH:
                    wayPointY += value;
                    break;
                case SOUTH:
                    wayPointY -= value;
                    break;
                case EAST:
                    wayPointX += value;
                    break;
                case WEST:
                    wayPointX -= value;
                    break;
                case LEFT:
                    switch (value) {
                        case 0:
                            break;
                        case 90:
                            int tempY = wayPointY;
                            wayPointY = wayPointX;
                            wayPointX = -tempY;
                            break;
                        case 180:
                            wayPointX = -wayPointX;
                            wayPointY = -wayPointY;
                            break;
                        case 270:
                            int tempX = wayPointX;
                            wayPointX = wayPointY;
                            wayPointY = -tempX;
                            break;
                        default:
                            System.err.println("Invalid value! " + value);
                            System.exit(0);
                            break;
                    }
                    break;
                case RIGHT:
                    switch (value) {
                        case 0:
                            break;
                        case 90:
                            int tempX = wayPointX;
                            wayPointX = wayPointY;
                            wayPointY = -tempX;
                            break;
                        case 180:
                            wayPointX = -wayPointX;
                            wayPointY = -wayPointY;
                            break;
                        case 270:
                            int tempY = wayPointY;
                            wayPointY = wayPointX;
                            wayPointX = -tempY;
                            break;
                        default:
                            System.err.println("Invalid value! " + value);
                            System.exit(0);
                            break;
                    }
                    break;
                case FORWARD:
                    shipX += wayPointX * value;
                    shipY += wayPointY * value;
                    break;
                default:
                    System.err.println("Invalid action! " + action);
                    System.exit(0);
                    break;
            }
        }
    }

    public int manhattanDistance(int vertical, int horizontal) {
        return Math.abs(vertical) + Math.abs(horizontal);
    }

    public int partA() {
        findDestination();
        return manhattanDistance(shipX, shipY);
    }

    public int partB() {
        shipX = 0;
        shipY = 0;
        findDestination2();
        return manhattanDistance(shipX, shipY);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day12 day12 = new Day12();
        System.out.println("Part A: " + day12.partA());
        System.out.println("Part B: " + day12.partB());
    }

}
