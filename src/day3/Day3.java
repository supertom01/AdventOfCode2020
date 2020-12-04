package day3;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day3 {

    private final ArrayList<char[]> input;

    /**
     * Construct the new object and converts the input file into a two dimensional array.
     */
    public Day3() {
        input = new ArrayList<>();
        try {
            String[] lines = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day3/input.txt");
            for(String line : lines) {
                input.add(line.toCharArray());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Go down through the input and count the number of trees (#).
     * @return The number of trees encountered.
     */
    private int goDown(int stepRight, int stepDown) {
        int nrTrees = 0;
        int x = 0; // X Coordinate
        int y = 0; // Y Coordinate
        int columns = input.get(0).length;

        // While we have not reached the bottom
        while(y < input.size()) {
            if(input.get(y)[x % columns] == '#') {
                nrTrees++;
            }
            x += stepRight;
            y += stepDown;
        }

        return nrTrees;
    }

    public static void main(String[] args) {

        // Part I
        Day3 day3 = new Day3();
        int treesFound = day3.goDown(3, 1);
        System.out.printf("Part I\nWe encountered %d trees!\n\n", treesFound);

        // Part II
        long r1d1 = day3.goDown(1,1);
        long r3d1 = day3.goDown(3,1);
        long r5d1 = day3.goDown(5,1);
        long r7d1 = day3.goDown(7,1);
        long r1d2 = day3.goDown(1,2);

        System.out.printf("Part II\nRight 1, down 1: %d\n" +
                "Right 3, down 1: %d\n" +
                "Right 5, down 1: %d\n" +
                "Right 7, down 1: %d\n" +
                "Right 1, down 2: %d\n" +
                "Multiplied: " + (r1d1 * r3d1 * r5d1 * r7d1 * r1d2) + "\n", r1d1, r3d1, r5d1, r7d1, r1d2);

    }
}
