package day10;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10 {

    private final Integer[] input;
    private ArrayList<List<Integer>> permutations;

    public Day10() throws FileNotFoundException {
        input = Reader.ReadInt("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day10/input.txt");
        permutations = new ArrayList<>();
    }

    /**
     * Sort an array.
     *
     * @param array the array to be sorted
     * @return the sorted array, from low to high
     */
    public Integer[] sortArray(Integer[] array) {
        Arrays.sort(array);
        return array;
    }

    /**
     * Part A.
     *
     * Count the number of increases in Jolts for each next adapter and return the differences of one jolt times the
     * number of differences of three jolt
     * @return nr of 1 jolt difference * nr of 3 jolt difference
     */
    public int partA() {
        int countJump1 = 1;
        int countJump3 = 1;
        Integer[] adapters = sortArray(input);
        for (int i = 1; i < adapters.length; i++) {
            int joltDifference = adapters[i] - adapters[i - 1];
            if(joltDifference == 1) {
                countJump1++;
            } else if (joltDifference == 3) {
                countJump3++;
            }
        }
        return countJump1 * countJump3;
    }

//    public boolean isValid(List<Integer> permutation) {
//        for (int i = 1; i < permutation.size(); i++) {
//            if(permutation.get(i) - permutation.get(i - 1) > 3) {
//                return false;
//            }
//        }
//        return true;
//    }

    public void createPermutations(Integer[] permutation) {
        if(!permutations.contains(Arrays.asList(permutation))) {
            permutations.add(Arrays.asList(permutation));
        }
        for (int i = 0; i < permutation.length - 2; i++) {
            int difference = permutation[i + 2] - permutation[i];
            if(difference <= 3) {
                Integer[] tempArray = new Integer[permutation.length - 1];
                System.arraycopy(permutation, 0, tempArray, 0, i + 1);
                System.arraycopy(permutation, i + 2, tempArray, i + 1, permutation.length - i - 2);
                if(!permutations.contains(Arrays.asList(tempArray))) {
                    permutations.add(Arrays.asList(tempArray));
                    createPermutations(tempArray);
                }
            }
        }
    }

    public int partB() {
        createPermutations(sortArray(input));
        return permutations.size();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day10 day10 = new Day10();
        System.out.println("Part A: " + day10.partA());
        System.out.println("Part B: " + day10.partB());
    }

}
