package day11;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Puzzle 11 of the Advent of Code calendar.
 * Seating System.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day11 {

    private final String[] input;

    public static final char EMPTY_SEAT = 'L';
    public static final char OCCUPIED_SEAT = '#';
    public static final char FLOOR = '.';

    public Day11() throws FileNotFoundException {
        input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day11/input.txt");
    }

    /**
     * Create a matrix from the input file.
     * @param input the input to transform
     * @return matrix
     */
    public char[][] createMatrix(String[] input) {
        char[][] matrix = new char[input.length][];
        for (int i = 0; i < input.length; i++) {
            matrix[i] = input[i].toCharArray();
        }
        return matrix;
    }

    /**
     * Part A.
     *
     * Update the seats for one iteration.
     *
     * @ensures If a seat is empty (<code>L</code>) and there are no occupied seats adjacent, the seat is occupied.
     *          If a seat is occupied (<code>#</code>) and four or more adjacent seats occupied, the seat is empty.
     *          Otherwise, the state does not change.
     * @param matrix the matrix to update.
     * @return the updated layout of the seats.
     */
    public char[][] updateSeats(char[][] matrix) {
        char[][] newMatrix = new char[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            newMatrix[i] = new char[matrix[i].length];
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char seatToCheck = matrix[i][j];
                int[][] surroundingIndexes = new int[][]{
                        new int[]{i - 1, j - 1},
                        new int[]{i - 1, j},
                        new int[]{i - 1, j + 1},
                        new int[]{i, j - 1},
                        new int[]{i, j + 1},
                        new int[]{i + 1, j - 1},
                        new int[]{i + 1, j},
                        new int[]{i + 1, j + 1}
                };

                if (seatToCheck == EMPTY_SEAT) {
                    boolean occupy = true;
                    for (int[] surrounding : surroundingIndexes) {
                        if (surrounding[0] >= 0 && surrounding[0] < matrix.length
                                && surrounding[1] >= 0 && surrounding[1] < matrix[i].length) {
                            if (matrix[surrounding[0]][surrounding[1]] == OCCUPIED_SEAT) {
                                occupy = false;
                                break;
                            }
                        }
                    }
                    if (occupy) {
                        newMatrix[i][j] = OCCUPIED_SEAT;
                    } else {
                        newMatrix[i][j] = EMPTY_SEAT;
                    }
                } else if (seatToCheck == OCCUPIED_SEAT) {
                    int neighbours = 0;
                    for (int[] surrounding : surroundingIndexes) {
                        if (surrounding[0] >= 0 && surrounding[0] < matrix.length
                                && surrounding[1] >= 0 && surrounding[1] < matrix[i].length) {
                            if (matrix[surrounding[0]][surrounding[1]] == OCCUPIED_SEAT) {
                                neighbours++;
                            }
                        }
                    }
                    if (neighbours >= 4) {
                        newMatrix[i][j] = EMPTY_SEAT;
                    } else {
                        newMatrix[i][j] = OCCUPIED_SEAT;
                    }
                } else if (seatToCheck == FLOOR) {
                    newMatrix[i][j] = FLOOR;
                }
            }
        }

        return newMatrix;
    }

    /**
     * Part B.
     *
     * Update the seats for one iteration.
     *
     * @ensures If five or more seats in the line of sight are occupied, a seat becomes empty (instead of four or more).
     *          If a seat is empty and there are no occupied seats in the line of sight, the seat is occupied.
     *          All seats in the direct line of sight are checked (instead of adjacent seats).
     * @param matrix the matrix to update.
     * @return the updated layout of the seats.
     */
    public char[][] updateSeatsB(char[][] matrix) {
        // Create a new, empty matrix with the proper dimensions.
        char[][] newMatrix = new char[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            newMatrix[i] = new char[matrix[i].length];
        }

        // Check each seat.
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char seatToCheck = matrix[i][j];
                boolean sitDown = true;
                int[][] seatsInSight = new int[8][];

                // Get all the seats in direct line of sight.
                for (int k = 1; k < Math.max(matrix.length, matrix[i].length); k++) {
                    int[][] surroundingIndexes = new int[][]{
                            new int[]{i - k, j - k},
                            new int[]{i - k, j},
                            new int[]{i - k, j + k},
                            new int[]{i, j - k},
                            new int[]{i, j + k},
                            new int[]{i + k, j - k},
                            new int[]{i + k, j},
                            new int[]{i + k, j + k}
                    };
                    for (int l = 0; l < surroundingIndexes.length; l++) {
                        boolean withinBounds = surroundingIndexes[l][0] >= 0 &&
                                surroundingIndexes[l][0] < matrix.length &&
                                surroundingIndexes[l][1] >= 0 &&
                                surroundingIndexes[l][1] < matrix[i].length;
                        boolean notFloor;
                        if(withinBounds) {
                            notFloor = matrix[surroundingIndexes[l][0]][surroundingIndexes[l][1]] != FLOOR;
                        } else {
                            notFloor = false;
                        }
                        boolean arrayEmpty = seatsInSight[l] == null;
                        boolean isSeat;
                        if(!arrayEmpty) {
                            isSeat = matrix[seatsInSight[l][0]][seatsInSight[l][1]] == EMPTY_SEAT ||
                                    matrix[seatsInSight[l][0]][seatsInSight[l][1]] == OCCUPIED_SEAT;
                        } else {
                            isSeat = false;
                        }

                        if (withinBounds && notFloor && (arrayEmpty || !isSeat)) {
                            seatsInSight[l] = surroundingIndexes[l];
                        }
                    }
                }



                // It the current seat is empty, a person may only sit if in all lines
                // of sight, no one is sitting. A line of sight is blocked by a seat.
                if (seatToCheck == EMPTY_SEAT) {
                    boolean occupy = true;
                    for (int[] surrounding : seatsInSight) {
                        if (surrounding != null && matrix[surrounding[0]][surrounding[1]] == OCCUPIED_SEAT) {
                            occupy = false;
                            break;
                        }
                    }
                    if (occupy) {
                        newMatrix[i][j] = OCCUPIED_SEAT;
                    } else {
                        newMatrix[i][j] = EMPTY_SEAT;
                    }
                } else if (seatToCheck == OCCUPIED_SEAT) {
                    int neighbours = 0;
                    for (int[] surrounding : seatsInSight) {
                        if (surrounding != null && matrix[surrounding[0]][surrounding[1]] == OCCUPIED_SEAT) {
                            neighbours++;
                        }
                    }
                    if (neighbours >= 5) {
                        newMatrix[i][j] = EMPTY_SEAT;
                    } else {
                        newMatrix[i][j] = OCCUPIED_SEAT;
                    }
                } else if (seatToCheck == FLOOR) {
                    newMatrix[i][j] = FLOOR;
                }
            }
        }
        return newMatrix;
}

    /**
     * Execute the needed iterations and calculations for part A of the puzzle.
     * @return the number of occupied seats when the layout has been stabilized.
     */
    public int partA() {
        char[][] prevMatrix;
        char[][] currentMatrix = createMatrix(input);
        do {
            prevMatrix = currentMatrix;
            currentMatrix = updateSeats(prevMatrix);
        } while (!Arrays.deepEquals(prevMatrix, currentMatrix));

        int countOccupiedSeats = 0;
        for (char[] row : currentMatrix) {
            for (char seat : row) {
                if (seat == OCCUPIED_SEAT) {
                    countOccupiedSeats++;
                }
            }
        }
        return countOccupiedSeats;
    }

    /**
     * Execute the needed iterations and calculations for part B of the puzzle.
     * @return the number of occupied seats when the layout has been stabilized.
     */
    public int partB() {
        char[][] prevMatrix;
        char[][] currentMatrix = createMatrix(input);
        do {
            prevMatrix = currentMatrix;
            currentMatrix = updateSeatsB(prevMatrix);
        } while (!Arrays.deepEquals(prevMatrix, currentMatrix));

        int countOccupiedSeats = 0;
        for (char[] row : currentMatrix) {
            for (char seat : row) {
                if (seat == OCCUPIED_SEAT) {
                    countOccupiedSeats++;
                }
            }
        }
        return countOccupiedSeats;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day11 day11 = new Day11();
        System.out.println("Part A: " + day11.partA());
        System.out.println("Part B: " + day11.partB());
    }

}
