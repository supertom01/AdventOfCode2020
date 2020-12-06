package day4;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Puzzle for day number 4 of the Advent of Code calendar.
 * You have to make sure that passports are valid.
 *
 * @author Tom Meulenkamp
 * @version v1.2
 *
 * v1.0 = Initial version
 * v1.1 = Replaced switch case with regex
 * v1.2 = Merged the part A and B functions into one function
 */
public class Day4 {

    private final String[] input;

    /**
     * The required fields in the passports.
     */
    private final ArrayList<String> requiredFields = new ArrayList<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));

    /**
     * A mapping from the passport field to the regex that checks if the value is valid.
     */
    private final HashMap<String, String> regexMapping = new HashMap<>() {{
        put("byr", "(19[2-9][0-9])|(200[0-2])");
        put("iyr", "(201[0-9])|2020");
        put("eyr", "(202[0-9])|2030");
        put("hgt", "(1[5-8][0-9]cm)|(19[0-3]cm)|(59in)|(6[0-9]in)|(7[0-6]in)");
        put("hcl", "#[a-f0-9]{6}");
        put("ecl", "amb|blu|brn|gry|grn|hzl|oth");
        put("pid", "[0-9]{9}");
    }};

    public Day4() throws FileNotFoundException {
        input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day4/input.txt");
    }

    /**
     * Merges all lines into single line passports.
     * @return Array with only properly formatted passports.
     */
    public String[] createPassports() {
        StringBuilder passport = new StringBuilder();
        ArrayList<String> passports = new ArrayList<>();
        for(String line : input) {
            if(line.equals("")) {
                passports.add(passport.toString());
                passport = new StringBuilder();
            } else {
                passport.append(line).append(" ");
            }
        }
        if(!passport.toString().equals("")) {
            passports.add(passport.toString());
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
        String[] pairs = passport.split(" ");
        for(String pair : pairs) {
            String key = pair.split(":")[0];
            String val = pair.split(":")[1];

            // We do not have to check the validity of cid.
            if(!key.equals("cid")) {
                Pattern p = Pattern.compile(regexMapping.get(key));
                Matcher m = p.matcher(val);
                if(!m.matches()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Run the testing procedure for both part A and B
     */
    public void run() {
        int countA = 0;
        int countB = 0;
        String[] passports = createPassports();
        for(String passport : passports) {
            if(checkValidityPartA(passport)) {
                countA++;
                if(checkValidityPartB(passport)) {
                    countB++;
                }
            }
        }
        System.out.println("Part A: " + countA);
        System.out.println("Part B: " + countB);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day4 day4 = new Day4();
        day4.run();
    }

}
