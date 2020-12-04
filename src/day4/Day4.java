package day4;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Puzzle for day number 4 of the Advent of Code calendar.
 * You have to make sure that passports are valid.
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day4 {

    private String[] input;
    private final ArrayList<String> requiredFields = new ArrayList<String>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));

    public Day4() throws FileNotFoundException {
        input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day4/input.txt");
    }

    /**
     * Merges all lines into single line passports.
     * @return Array with only properly formatted passports.
     */
    public String[] createPassports() {
        String passport = "";
        ArrayList<String> passports = new ArrayList<>();
        for(String line : input) {
            if(line.equals("")) {
                passports.add(passport);
                passport = "";
            } else {
                passport += line + " ";
            }
        }
        if(!passport.equals("")) {
            passports.add(passport);
        }
        return passports.toArray(String[]::new);
    }

    /**
     * Check if a passport has all the required fields.
     * @param passport the passport to check
     * @return true if the passport contains all the required fields.
     */
    public boolean checkValidityPartA(String passport) {
        String[] pairs = passport.split(" ");
        ArrayList<String> found_keys = new ArrayList<>();
        for(String pair : pairs) {
            String key = pair.split(":")[0];
            if (!found_keys.contains(key) && requiredFields.contains(key)) {
                found_keys.add(key);
            }
        }
        return found_keys.size() >= 7;
    }

    /**
     * Checks if a passport has all the required fields and that these fields have valid entries.
     * @param passport the passport to check.
     * @return true if the passport is deemed valid
     */
    public boolean checkValidityPartB(String passport) {
        if(!checkValidityPartA(passport)) {
            return false;
        }
        String[] pairs = passport.split(" ");
        for(String pair : pairs) {
            String key = pair.split(":")[0];
            String val = pair.split(":")[1];

            switch (key) {
                case "byr":
                    // Year of birth should be between 1920 and 2002
                    if(!((1920 <= Integer.parseInt(val)) && (Integer.parseInt(val) <= 2002))) {
                        return false;
                    }
                    break;
                case "iyr":
                    // Year of issue should be between 2010 and 2020
                    if(!((2010 <= Integer.parseInt(val)) && (Integer.parseInt(val) <= 2020))) {
                        return false;
                    }
                    break;
                case "eyr":
                    // Year of expiry should be between 2020 and 2030
                    if(!((2020 <= Integer.parseInt(val)) && (Integer.parseInt(val) <= 2030))) {
                        return false;
                    }
                    break;
                case "hgt":
                    // Height should contains cm or in
                    if(val.contains("cm") || val.contains("in")) {
                        int length = Integer.parseInt(val.substring(0, val.length() - 2));
                        // If cm then the person should be between 150 and 193 cm
                        if(val.contains("cm") && !(150 <= length && length <= 193)) {
                            return false;
                        }
                        // If in then the person should be between 59 and 76 in
                        else if (val.contains("in") && !(59 <= length && length <= 76)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case "hcl":
                    // Make sure that the hair colour has a valid hex color code.
                    Pattern p = Pattern.compile("#[a-f0-9]{6}");
                    Matcher m = p.matcher(val);
                    if(!m.matches()) {
                        return false;
                    }
                    break;
                case "ecl":
                    // The eye color can only be amb, blu, brn, gry, grn, hzl or oth.
                    if(!(val.equals("amb") || val.equals("blu") || val.equals("brn") ||
                            val.equals("gry") || val.equals("grn") || val.equals("hzl") ||
                            val.equals("oth"))) {
                        return false;
                    }
                    break;
                case "pid":
                    // A person ID should contain only 9 integers.
                    Pattern p1 = Pattern.compile("[0-9]{9}");
                    Matcher m1 = p1.matcher(val);
                    if(!m1.matches()) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public void part1() {
        int count = 0;
        String[] passports = createPassports();
        for(String passport : passports) {
            if(checkValidityPartA(passport)) {
                count++;
            }
        }
        System.out.println("Part A: " + count);
    }

    public void part2() {
        int count = 0;
        String[] passports = createPassports();
        for(String passport : passports) {
            if(checkValidityPartB(passport)) {
                count++;
            }
        }
        System.out.println("Part B: " + count);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day4 day4 = new Day4();
        day4.part1();
        day4.part2();
    }

}
