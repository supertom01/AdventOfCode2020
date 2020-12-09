package day9;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Puzzle 9 of the Advent of Code calendar.
 * Encoding Error.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day9 {

    private final Integer[] input;
    public static final int PREAMBLE = 25;

    public Day9() throws FileNotFoundException {
        input = Reader.ReadInt("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day9/input.txt");
    }

    /**
     * Part A of the puzzle.
     *
     * Loop through the input and check for every input > PREAMBLE that it is the sum of two values inside the min max
     * index range. If this is not the case, return this value.
     * @return the value that cannot be added up by the previous PREAMBLE values.
     */
    public int partA() {
        int minIndex = 0;
        int maxIndex = PREAMBLE;
        for (int i = PREAMBLE; i < input.length; i++) {
            boolean result = false;

            // Check if the current value can be created with adding two previous values within the min and max boundries.
            for (int j = minIndex; j < maxIndex; j++) {
                for (int k = minIndex; k < maxIndex; k++) {
                    if (input[i] == input[j] + input[k]) {
                        result = true;
                        break;
                    }
                }
            }
            if(result) {
                minIndex++;
                maxIndex++;
            } else {
                return input[i];
            }
        }
        return -1;
    }

    /**
     * Convert the selected indexes into values, sort the values list from low to high and add together the lowest and
     * highest values.
     *
     * @param selectedIndexes the indexes to process.
     * @return the sum of the lowest and highest values.
     */
    public int getEncryptionWeakness(ArrayList<Integer> selectedIndexes) {
        ArrayList<Integer> selectedValues = new ArrayList<>();
        for(Integer index : selectedIndexes) {
            selectedValues.add(input[index]);
        }
        Collections.sort(selectedValues);
        return selectedValues.get(0) + selectedValues.get(selectedValues.size() - 1);
    }

    /**
     * Part B of the puzzle
     *
     * Find the list of contiguous values that add up together towards the goal number (answer of part A).
     * Loop over all the inputs and add them to the sum, if the sum is equal to the goal, get the encryption weakness.
     * If the sum is higher than the goal, subtract the oldest addition and add it to the excluded list, then reset all
     * other values and begin again.
     *
     * @param goal the total to reach
     * @return the sum of the highest and lowest value in the contiguous range that add together to the goal.
     */
    public int partB(int goal) {
        ArrayList<Integer> selectedIndexes = new ArrayList<>();
        ArrayList<Integer> excludedIndexes = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            if(!excludedIndexes.contains(i)) {
                sum += input[i];
                selectedIndexes.add(i);
            }
            if(sum == goal) {
                return getEncryptionWeakness(selectedIndexes);
            } else if (sum > goal) {
                sum -= input[selectedIndexes.get(0)];
                excludedIndexes.add(selectedIndexes.get(0));
                selectedIndexes.remove(0);
                if(sum == goal) {
                    return getEncryptionWeakness(selectedIndexes);
                } else {
                    selectedIndexes = new ArrayList<>();
                    sum = 0;
                    i = 0;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day9 day9 = new Day9();
        System.out.println("Part A: " + day9.partA());
        System.out.println("Part B: " + day9.partB(day9.partA()));
    }

}
