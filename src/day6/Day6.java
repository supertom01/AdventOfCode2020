package day6;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Puzzle 6 of the Advent of Code christmas calendar.
 * Custom Customs.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day6 {

    private final String[] input;
    private final String[] alphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", 
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public Day6() throws FileNotFoundException {
        this.input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day6/input.txt");
    }

    /**
     * Collect all the provided answers provided in the forms.
     * @param input Puzzle input
     * @return An array containing on each index a form of one group.
     */
    public String[] organizeFormsA(String[] input) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder newLine = new StringBuilder();
        for(String line : input) {
            if(!line.equals("")) {
                newLine.append(line);
            } else {
                result.add(newLine.toString());
                newLine = new StringBuilder();
            }
        }
        if(!newLine.toString().equals("")) {
            result.add(newLine.toString());
        }
        return result.toArray(String[]::new);
    }

    /**
     * Check how often yes is answered by one group on unique questions.
     * @param form the group form to check
     * @return the number of unique characters in a form
     */
    public int readAnswersA(String form) {
        ArrayList<Character> answers = new ArrayList<>();
        for(char answer : form.toCharArray()) {
            if(!answers.contains(answer)) {
                answers.add(answer);
            }
        }
        return answers.size();
    }

    /**
     * Calculate the number of answers for all forms.
     * @return the total number of answers
     */
    public int partA() {
        String [] forms = organizeFormsA(input);
        int count = 0;
        for(String form : forms) {
            count += readAnswersA(form);
        }
        return count;
    }

    /**
     * Collect all the provided answers provided in the forms, do not merge answers within groups.
     * @param input Puzzle input
     * @return An array containing an array for each group, in which answers are collected for each person.
     */
    public String[][] organizeFormsB(String[] input) {
        ArrayList<String[]> result = new ArrayList<>();
        ArrayList<String> group = new ArrayList<>();
        for(String line : input) {
            if(!line.equals("")) {
                group.add(line);
            } else {
                result.add(group.toArray(String[]::new));
                group = new ArrayList<>();
            }
        }
        if(!group.isEmpty()) {
            result.add(group.toArray(String[]::new));
        }
        return result.toArray(String[][]::new);
    }

    /**
     * Check for each group on how much questions they all answered yes.
     * @param form the group form to check
     * @return the number of questions answered yes, by all people in the group.
     */
    public int readAnswersB(String[] form) {
        if(form.length == 1) {
            return readAnswersA(form[0]);
        }
        ArrayList<String> allAnswered = new ArrayList<>();
        for (String letter : this.alphabet) {
            boolean containLetter = true;
            for (String person : form) {
                if(!person.contains(letter)) {
                    containLetter = false;
                    break;
                }
            }
            if(containLetter) {
                allAnswered.add(letter);
            }
        }
        return allAnswered.size();
    }

    /**
     * Calculate the total number of questions for which groups answered yes as a whole.
     * @return the total number of questions answered true.
     */
    public int partB() {
        String[][] forms = organizeFormsB(input);
        int count = 0;
        for(String[] form : forms) {
            count += readAnswersB(form);
        }
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day6 day6 = new Day6();
        System.out.println("Part A: " + day6.partA());
        System.out.println("Part B: " + day6.partB());
    }

}
