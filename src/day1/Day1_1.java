package day1;

import java.io.FileNotFoundException;

/**
 * Day 1 of the Advent of Code 2020 Calendar.
 *
 * Puzzle 1:
 * The goal is to find the two numbers that sum up to 2020,
 * and the multiplication of these answers is the solution.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day1_1 {

    public static Integer[] input = new Integer[0];

    static {
        try {
            input = util.Reader.ReadInt("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day1/input.txt");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        for (int k : input) {
            for (int i : input) {
                if (k != i && k + i == 2020) {
                    System.out.printf("Result found! Values %s and %s are together 2020 and multiplied are %s \n",
                            k, i, k * i);
                }
            }
        }

    }

}
