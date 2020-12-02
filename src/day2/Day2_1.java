package day2;

import java.io.FileNotFoundException;

/**
 * Day 2 of the Advent of Code 2020 Calendar.
 *
 * Puzzle 1:
 * The goals is to find all correct passwords.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day2_1 {

    private String[] input;

    /**
     * Constructs this class and reads the input file.
     */
    public Day2_1() {
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
        String password = split[2];

        int min = Integer.parseInt(amount.split("-")[0]);
        int max = Integer.parseInt(amount.split("-")[1]);

        int count = 0;

        for(char c : password.toCharArray()) {
            if(c == letter) {
                count++;
            }
        }

        return count >= min && count <= max;
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
        Day2_1 day2_1 = new Day2_1();
        System.out.println(day2_1.count());
    }

}
