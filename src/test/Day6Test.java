package test;

import day6.Day6;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Reader;

import java.io.FileNotFoundException;

class Day6Test {

    private Day6 day6;
    private String[] testInput;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        day6 = new Day6();
        testInput = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/test/day6.txt");
    }

    @Test
    void organizeFormsA() {
        String[] expected = new String[]{"abc", "abc", "abac", "aaaa", "b"};
        Assertions.assertArrayEquals(expected, day6.organizeFormsA(testInput));
    }

    @Test
    void readAnswersA() {
        String[] forms = day6.organizeFormsA(testInput);
        Assertions.assertEquals(3, day6.countAnswersA(forms[0]));
        Assertions.assertEquals(3, day6.countAnswersA(forms[1]));
        Assertions.assertEquals(3, day6.countAnswersA(forms[2]));
        Assertions.assertEquals(1, day6.countAnswersA(forms[3]));
        Assertions.assertEquals(1, day6.countAnswersA(forms[4]));
    }

    @Test
    void readAnswersB() {
        String[][] forms = day6.organizeFormsB(testInput);
        Assertions.assertEquals(3, day6.countAnswersB(forms[0]));
        Assertions.assertEquals(0, day6.countAnswersB(forms[1]));
        Assertions.assertEquals(1, day6.countAnswersB(forms[2]));
        Assertions.assertEquals(1, day6.countAnswersB(forms[3]));
        Assertions.assertEquals(1, day6.countAnswersB(forms[4]));
    }

}