package test;

import day5.Day5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    public Day5 day5;
    public static final String TEST_1 = "FBFBBFFRLR";
    public static final String TEST_2 = "BFFFBBFRRR";
    public static final String TEST_3 = "FFFBBBFRRR";
    public static final String TEST_4 = "BBFFBBFRLL";

    @BeforeEach
    void setUp() throws FileNotFoundException {
        day5 = new Day5();
    }

    @Test
    void getRow() {
        assertEquals(44, day5.getRow(TEST_1));
        assertEquals(70, day5.getRow(TEST_2));
        assertEquals(14, day5.getRow(TEST_3));
        assertEquals(102, day5.getRow(TEST_4));
    }

    @Test
    void getColumn() {
        assertEquals(5, day5.getColumn(TEST_1));
        assertEquals(7, day5.getColumn(TEST_2));
        assertEquals(7, day5.getColumn(TEST_3));
        assertEquals(4, day5.getColumn(TEST_4));
    }

    @Test
    void getSeatID() {
        assertEquals(357, day5.getSeatID(TEST_1));
        assertEquals(567, day5.getSeatID(TEST_2));
        assertEquals(119, day5.getSeatID(TEST_3));
        assertEquals(820, day5.getSeatID(TEST_4));
    }
}