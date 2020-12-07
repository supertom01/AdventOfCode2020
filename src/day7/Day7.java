package day7;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Puzzle 7 of the Advent of Code calendar.
 * Handy Haversacks
 *
 * @author Tom Meulenkamp
 * @version v1.0
 */
public class Day7 {

    private final String[] input;
    private final HashMap<String, HashMap<String, Integer>> bags;

    public Day7() throws FileNotFoundException {
        input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day7/input.txt");
        bags = createDataStructure(input);
    }

    /**
     * Create a data structure where each bag has a HashMap with the type and number bags inside of it.
     * @param input The input for which the data structure should be generated.
     * @return the finished data structure
     */
    public HashMap<String, HashMap<String, Integer>> createDataStructure(String[] input) {
        HashMap<String, HashMap<String, Integer>> bags = new HashMap<>();
        for(String rule : input) {
            String[] split = rule.split(" bags contain ");
            String key = split[0];
            if(split[1].contains("no other bags")) {
                bags.put(key, null);
            } else {
                String[] bagsInBag = split[1].split(", ");
                HashMap<String, Integer> bagInbag = new HashMap<>();
                for(String bag : bagsInBag) {
                    Integer amount = Integer.parseInt(bag.substring(0, 1));
                    String bagName = bag.substring(2).replace("bags", "").replace("bag", "").replace(".", "");
                    bagName = bagName.substring(0, bagName.length() - 1);
                    bagInbag.put(bagName, amount);
                }
                bags.put(key, bagInbag);
            }
        }
        return bags;
    }

    /**
     * Count the number of bags that contain this bag
     * @param desiredBag The bag for which to count
     * @return the number of bags that contain this bag
     */
    public int count(String desiredBag) {
        int counter = 0;
        for(String key : bags.keySet()) {
            if(bags.get(key) != null) {
                if(countNrOfPossibleBags(bags.get(key), desiredBag) > 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int countNrOfPossibleBags(HashMap<String, Integer> map, String bag) {
        int counter = 0;
        for(String key : map.keySet()) {
            if(key.equals(bag)) {
                return 1;
            } else {
                if(this.bags.get(key) != null) {
                    counter += countNrOfPossibleBags(this.bags.get(key), bag);
                }
            }
        }
        return counter;
    }

    /**
     * Get the number of bags inside of a bag.
     * @param bag The bag to check
     * @return the number of bags in the bag.
     */
    public int getNumberOfBags(String bag) {
        HashMap<String, Integer> bagsInBag = bags.get(bag);
        if(bagsInBag == null) {
            return 0;
        }
        int counter = 0;
        for(String bagInBag : bagsInBag.keySet()) {
            if(bags.get(bagInBag) == null) {
                counter += bagsInBag.get(bagInBag);
            } else {
                counter += bagsInBag.get(bagInBag) * getNumberOfBags(bagInBag);
            }
        }
        // Include the bag inside of the bag itself
        counter++;
        return counter;
    }

    public void run() {
        System.out.println("Part A: " + count("shiny gold"));
        System.out.println("Part B: " + (getNumberOfBags("shiny gold") - 1));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day7 day7 = new Day7();
        day7.run();
    }
}
