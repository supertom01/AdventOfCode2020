package day1;

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

    public static void main(String[] args) {

        for (int k : Input.input) {
            for (int i : Input.input) {
                if (k != i && k + i == 2020) {
                    System.out.printf("Result found! Values %s and %s are together 2020 and multiplied are %s \n",
                            k, i, k * i);
                }
            }
        }

    }

}
