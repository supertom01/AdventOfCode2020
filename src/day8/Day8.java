package day8;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * The 8th puzzle of the Advent of Code calendar.
 * Handheld Halting.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day8 {

    /**
     * The instruction object contain the operation and argument for the instruction.
     */
    public static class Instruction {

        private final String operation;
        private final int argument;

        public Instruction(String instruction) {
            this.operation = instruction.split(" ")[0];
            this.argument = Integer.parseInt(instruction.split(" ")[1]);
        }

        public String getOperation() {
            return operation;
        }

        public Integer getArgument() {
            if(this.operation.equals("nop")) {
                // Should never happen, but in case.
                return null;
            }
            return this.argument;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Instruction that = (Instruction) o;
            return getArgument() == that.getArgument() && Objects.equals(getOperation(), that.getOperation());
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "operation='" + operation + '\'' +
                    ", argument=" + argument +
                    '}';
        }
    }

    private final Instruction[] instructions;
    private ArrayList<Integer> visitedLines;
    private int accumulator;

    public Day8() throws FileNotFoundException {
        this("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day8/input.txt");
    }

    /**
     * Create the day 8 object and create the instructions array.
     * @param filepath the filepath to the input file.
     * @throws FileNotFoundException thrown when the input file is not found.
     */
    public Day8(String filepath) throws FileNotFoundException {
        String[] input = Reader.ReadString(filepath);
        this.instructions = new Instruction[input.length];
        for (int i = 0; i < input.length; i++) {
            this.instructions[i] = new Instruction(input[i]);
        }

        visitedLines = new ArrayList<>();
        accumulator = 0;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    /**
     * Run the current instruction and update the accumulator accordingly.
     * @param instruction the instruction to run.
     * @return the number of jumps we have to take.
     */
    public int runInstruction(Instruction instruction) {
        switch(instruction.getOperation()) {
            case "acc":
                accumulator += instruction.getArgument();
                break;
            case "jmp":
                return instruction.getArgument();
            default:
                return 1;
        }
        return 1;
    }

    /**
     * Check if the current instruction set terminates.
     *
     * @param instructions the instruction set to check.
     * @return true if the set can terminate.
     */
    public boolean terminates(Instruction[] instructions) {
        ArrayList<Integer> visitedLines = new ArrayList<>();
        for (int i = 0; i < instructions.length; i++) {
            if(!visitedLines.contains(i)) {
                visitedLines.add(i);
                i += runInstruction(instructions[i]) - 1;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the correct codeset which will make a loop-free program.
     *
     * @param initialInstructions an intial set of instructions (puzzle input)
     * @return the correct set, if any.
     */
    public Instruction[] findCorrectCode(Instruction[] initialInstructions) {
        for (int i = 0; i < initialInstructions.length; i++) {
            if(initialInstructions[i].getOperation().equals("jmp")) {
                Instruction[] testSet = Arrays.copyOf(initialInstructions, initialInstructions.length);
                testSet[i] = new Instruction("nop " + initialInstructions[i].getArgument());
                if(terminates(testSet)) {
                    this.accumulator = 0;
                    return testSet;
                }
            }
        }
        return null;
    }

    /**
     * Calculate the accumulator for the given instruction set.
     *
     * If a line in the instruction set is visited for a second time,
     * terminate the program and return the accumulator.
     *
     * @param instructions the instruction set to run.
     * @return the accumulator at time of termination.
     */
    public int calculateAccumulator(Instruction[] instructions) {
        for (int i = 0; i < instructions.length; i++) {
            if(!visitedLines.contains(i)) {
                visitedLines.add(i);
                i += runInstruction(instructions[i]) - 1;
            } else {
                return accumulator;
            }
        }
        return accumulator;
    }

    public void run() {
        System.out.println("Part A: " + calculateAccumulator(instructions));
        accumulator = 0;
        visitedLines = new ArrayList<>();
        System.out.println("Part B: " + calculateAccumulator(findCorrectCode(instructions)));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day8 day8 = new Day8();
        day8.run();
    }

}
