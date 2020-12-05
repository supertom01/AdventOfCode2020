package day5;

import org.jetbrains.annotations.NotNull;
import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Puzzle for day number 5 of the Advent of Code calendar.
 * You have to check boarding passes and find your own seat.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day5 {

    private final String[] input;

    /**
     * Rows from 0 - 127, Columns from 0 - 7
     */
    public static final int ROWS = 128;
    public static final int COLUMNS = 8;

    public Day5() throws FileNotFoundException {
        this.input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day5/input.txt");
    }

    /**
     * Get the row number from the selected boarding pass.
     * @param boardingPass the boarding pass to check
     * @return the row number
     * @ensures 0 <= row <= 127
     * @requires boardingPass != null
     */
    public int getRow(@NotNull String boardingPass) {
        int minRow = 0;
        int maxRow = ROWS;
        char[] indicators = boardingPass.toCharArray();

        // The first 7 indicators specify the row
        for (int i = 0; i < 7; i++) {
            if(indicators[i] == 'F') {
                maxRow = maxRow - ((maxRow - minRow) / 2);
            } else {
                minRow = minRow + ((maxRow - minRow) / 2);
            }
        }

        return minRow;
    }

    /**
     * Get the column number from the selected boarding pass.
     * @param boardingPass the boarding pass to check
     * @return the column number
     * @ensures 0 <= column <= 7
     * @requires boardingPass != null
     */
    public int getColumn(@NotNull String boardingPass) {
        int minColumn = 0;
        int maxColumn = COLUMNS;
        char[] indicators = boardingPass.toCharArray();

        // The last 3 indicators specify the column
        for(int i = 7; i < 10; i++) {
            if(indicators[i] == 'L') {
                maxColumn = maxColumn - ((maxColumn - minColumn) / 2);
            } else {
                minColumn = minColumn + ((maxColumn - minColumn) / 2);
            }
        }

        return minColumn;
    }

    /**
     * Calculate the seat ID of the boarding pass.
     * @param boardingPass the boarding pass to check
     * @return the seat ID (row * 8) + column
     */
    public int getSeatID(@NotNull String boardingPass) {
        return (getRow(boardingPass) * 8) + getColumn(boardingPass);
    }

    /**
     * Calculate the highest seat ID
     * @return the highest seat ID
     */
    public int partA() {
        int id = 0;
        for(String boardingPass : input) {
            int seatId = getSeatID(boardingPass);
            if(seatId > id) {
                id = seatId;
            }
        }
        return id;
    }

    /**
     * Check which seat ID is missing from all the boarding passes.
     * @return the missing seat ID, this is your own seat ID
     */
    public int partB() {
        ArrayList<Integer> ids = new ArrayList<>();
        for(String boardingPass : input) {
            ids.add(getSeatID(boardingPass));
        }
        Collections.sort(ids);

        ArrayList<Integer> results = new ArrayList<>();
        for(int i = 1; i < ids.size() ; i++) {
            if(ids.get(i - 1) + 1 != ids.get(i)) {
                results.add(ids.get(i - 1));
                results.add(ids.get(i));
            }
        }

        return (results.get(0) + results.get(1)) / 2;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day5 day5 = new Day5();
        System.out.println("Part A: " + day5.partA());
        System.out.println("Part B: " + day5.partB());
    }

}
