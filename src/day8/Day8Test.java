package day8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Reader;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Day8Test {

    private Day8 day8;
    public Day8.Instruction[] CORRECT_PROGRAM;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        day8 = new Day8("C:/Users/meule/IdeaProjects/adventOfCode2020/src/test/day8.txt");
        String[] correctInput = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/test/day8_correct.txt");
        CORRECT_PROGRAM = new Day8.Instruction[correctInput.length];
        for (int i = 0; i < correctInput.length; i++) {
            CORRECT_PROGRAM[i] = new Day8.Instruction(correctInput[i]);
        }
    }

    @Test
    void findCorrectCode() {
        Day8.Instruction[] instructions = day8.findCorrectCode(day8.getInstructions());
        assertArrayEquals(CORRECT_PROGRAM, instructions);
    }
}