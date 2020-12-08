package test;

import day7.Day7;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Reader;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    private String[] input;
    private String[] input2;
    private Day7 day7;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/test/day7.txt");
        input2 = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/test/day7_2.txt");
        day7 = new Day7();
        day7.setBags(day7.createDataStructure(input));
    }

    @Test
    void createDataStructure() {
        HashMap<String, HashMap<String, Integer>> testMap = new HashMap<>() {{
            put("bright white", new HashMap<>() {{put("shiny gold", 1); }});
            put("light red", new HashMap<>() {{put("bright white", 1); put("muted yellow", 2); }});
            put("dark orange", new HashMap<>() {{put("bright white", 3); put("muted yellow", 4); }});
            put("muted yellow", new HashMap<>() {{put("shiny gold", 2); put("faded blue", 9); }});
            put("shiny gold", new HashMap<>() {{put("dark olive", 1); put("vibrant plum", 2); }});
            put("dark olive", new HashMap<>() {{put("faded blue", 3); put("dotted black", 4); }});
            put("vibrant plum", new HashMap<>() {{put("faded blue", 5); put("dotted black", 6); }});
            put("faded blue", null);
            put("dotted black", null);
        }};

        HashMap<String, HashMap<String, Integer>> resultMap = day7.createDataStructure(input);
        assertEquals(testMap, resultMap);
    }

    @Test
    void nrOfBagsContainBag() {
        assertEquals(4, day7.nrOfBagsContainBag("shiny gold"));
    }

    @Test
    void getNumberOfBags() {
        assertEquals(32, day7.getNumberOfBags("shiny gold") - 1);
        day7.setBags(day7.createDataStructure(input2));
        assertEquals(126, day7.getNumberOfBags("shiny gold") - 1);
    }

}