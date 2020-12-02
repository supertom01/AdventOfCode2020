package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reader class for reading the puzzle inputs.
 *
 * @author Tom Meulenkamp
 */
public class Reader {

    /**
     * Read a file and return the an array with every line on a new index.
     * @param filepath the file to read.
     * @return An array with the contents of the file
     * @throws FileNotFoundException Thrown when the file is not found (duhh...)
     */
    public static String[] ReadString(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextLine());
        }
        scanner.close();

        return list.toArray(String[]::new);
    }

    /**
     * Read a file and return the an array with every line on a new index.
     * @param filepath the file to read.
     * @return An array with the contents of the file
     * @throws FileNotFoundException Thrown when the file is not found (duhh...)
     */
    public static Integer[] ReadInt(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        ArrayList<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        scanner.close();

        return list.toArray(Integer[]::new);
    }

}
