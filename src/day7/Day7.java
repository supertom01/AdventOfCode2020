package day7;

import util.Reader;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Puzzle 7 of the Advent of Code calendar.
 * Handy Haversacks
 *
 * @author Tom Meulenkamp
 * @version v1.1
 *
 * v1.0 = Initial version
 * v1.1 = Rewritten the nrOfBagsInContainBag function, should be more readable
 */
public class Day7 {

    // TODO: 2020-12-07 Might want to try and take a look to do the nrOfBagsContainBag function with a boolean...

    /**
     * The data structure containing the mapping from one bag to the bags inside of it.
     * {Red -> {Yellow -> 2}, {Blue -> 3}}, {Blue -> {Yellow -> 1}}, {Yellow -> null}
     * If a bag points to null, then it does not contain any more bags.
     */
    private HashMap<String, HashMap<String, Integer>> bags;

    public Day7() throws FileNotFoundException {
        String[] input = Reader.ReadString("C:/Users/meule/IdeaProjects/adventOfCode2020/src/day7/input.txt");
        bags = createDataStructure(input);
    }

    public void setBags(HashMap<String, HashMap<String, Integer>> bags) {
        this.bags = bags;
    }

    /**
     * Create a data structure where each bag has a HashMap with the type and number bags inside of it.
     *
     * @param input The input for which the data structure should be generated.
     * @return the finished data structure
     */
    public HashMap<String, HashMap<String, Integer>> createDataStructure(String[] input) {
        HashMap<String, HashMap<String, Integer>> bags = new HashMap<>();
        for (String rule : input) {
            String[] split = rule.split(" bags contain ");
            String key = split[0];
            if (split[1].contains("no other bags")) {
                bags.put(key, null);
            } else {
                String[] bagsInBag = split[1].split(", ");
                HashMap<String, Integer> bagInbag = new HashMap<>();
                for (String bag : bagsInBag) {
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
     * Calculate the number of bags that contain this bag.
     * Makes use of its overloaded version which is running recursively through each bag.
     *
     * @param bagName the name of the bag to check
     * @return the number of bags that contain this bag
     */
    public int nrOfBagsContainBag(String bagName) {
        int nrOfBags = 0;
        for (String bag : bags.keySet()) {
            if(nrOfBagsContainBag(bag, bagName) > 0) {
                nrOfBags++;
            }
        }
        return nrOfBags;
    }

    /**
     * Calculate how often a bag contains the bag that we search.
     *
     * @param bagToSearch The bag that we are searching
     * @param bagToFind The bag that we are looking for
     * @return the amount of times that the bag that we are looking for
     * occurs inside of the bag that we are searching
     */
    public int nrOfBagsContainBag(String bagToSearch, String bagToFind) {
        int nrOfBags = 0;
        // Make sure that the bag has bags inside of it.
        if (bags.get(bagToSearch) != null) {
            for (String bagInBag : bags.get(bagToSearch).keySet()) {
                if (bagInBag.equals(bagToFind)) {
                    nrOfBags++;
                } else {
                    nrOfBags += nrOfBagsContainBag(bagInBag, bagToFind);
                }
            }
        }

        return nrOfBags;
    }

    /**
     * Get the number of bags inside of a bag.
     *
     * @param bag The bag to check
     * @return the number of bags in the bag.
     */
    public int getNumberOfBags(String bag) {
        HashMap<String, Integer> bagsInBag = bags.get(bag);
        if (bagsInBag == null) {
            return 0;
        }
        int counter = 0;
        for (String bagInBag : bagsInBag.keySet()) {
            if (bags.get(bagInBag) == null) {
                counter += bagsInBag.get(bagInBag);
            } else {
                counter += bagsInBag.get(bagInBag) * getNumberOfBags(bagInBag);
            }
        }
        // Include the bag inside of the bag itself
        counter++;
        return counter;
    }

    /**
     * Run the puzzle output through the algorithms.
     */
    public void run() {
        System.out.println("Part A: " + nrOfBagsContainBag("shiny gold"));
        System.out.println("Part B: " + (getNumberOfBags("shiny gold") - 1));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Day7 day7 = new Day7();
        day7.run();
    }
}
