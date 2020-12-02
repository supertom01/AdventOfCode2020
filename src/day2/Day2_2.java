package day2;

import java.io.FileNotFoundException;

/**
 * Day 2 of the Advent of Code 2020 Calendar.
 *
 * Puzzle 2:
 * The goals is to find all correct passwords.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day2_2 {

    private String[] input;

    /**
     * Constructs this class and reads the input file.
     */
    public Day2_2() {
        try {
            input = util.Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day2/input.txt");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Check if a line in the file matches the corporate password policy.
     * @param line The line to be checked.
     * @return true if the password is according to the corporate policy.
     */
    private boolean isValid(String line) {
        String[] split = line.split(" ");
        String amount = split[0];
        char letter = split[1].charAt(0);
        char[] password = split[2].toCharArray();

        int first_index = Integer.parseInt(amount.split("-")[0]);
        int second_index = Integer.parseInt(amount.split("-")[1]);

        return (password[first_index - 1] == letter || password[second_index - 1] == letter)
                && !(password[first_index - 1] == letter && password[second_index - 1] == letter);
    }

    /**
     * Check how many passwords are correct.
     * @return the number of correct passwords
     */
    private int count() {
        int count = 0;
        for (String line : input) {
            if(isValid(line)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Day2_2 day2_1 = new Day2_2();
        System.out.println(day2_1.count());
    }

}
