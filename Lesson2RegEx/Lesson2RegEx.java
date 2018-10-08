
/**
 * Lesson2RegEx.java
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

public class Lesson2RegEx {

    static List<String> panIds = new LinkedList<String>();
    static HashMap<String, Double> macAdresses = new HashMap<String, Double>();

    public static void main(String[] args) {

        // Get the file name from the command line...
        String filename = null;
        for (String s : args) {
            filename = s;
        }

        // ...or use a default file name
        if (filename == null)
            filename = "neighbor-dump.txt";

        // Open a file stream and process each line (Java 8+ only)
        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> process(line));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Print the required output
        System.out.println("Results are as follows:");
        System.out.println("- List of PAN IDs (Total of " + Integer.toString(panIds.size()) + ")");
        for (String s : panIds) {
            System.out.println("PANID = " + s);
        }

        System.out.println("- List of MAC Addresses (Total of " + Integer.toString(macAdresses.size()) + ")");
        for (String key : macAdresses.keySet()) {
            System.out.println(key);
        }

        System.out.println("- List of RF_RSSI_M values for each MAC address");
        for (String key : macAdresses.keySet()) {
            System.out.println(key + " " + macAdresses.get(key));
        }

    }

    private static void process(String s) {

        // Call each processing rule method
        findPanIds(s);
        findMacAddresses(s);
    }

    // Method for finding PAN IDs
    private static void findPanIds(String s) {

        try {
            Pattern regex = Pattern.compile("[Pp][Aa][Nn][Ii][Dd]\\s+=\\s+(?<panID>[0-9].*)");
            Matcher regexMatcher = regex.matcher(s);
            if (regexMatcher.find()) {
                panIds.add(regexMatcher.group("panID"));
            }
        } catch (PatternSyntaxException ex) {
            ex.printStackTrace();
        }

    }

    // Method for finding MAC addresses
    private static void findMacAddresses(String s) {

        try {
            Pattern regex = Pattern.compile(
                    "(?<mac>[0-9a-f]{16})\\s+BPD\\s+(true|false)\\s+[0-9]+\\s+FSK_75\\s+FSK_75\\s+(?<rssi>[\\d-.]+)");
            Matcher regexMatcher = regex.matcher(s);
            if (regexMatcher.find()) {
                macAdresses.put(regexMatcher.group("mac"), Double.parseDouble(regexMatcher.group("rssi")));
            }
        } catch (PatternSyntaxException ex) {
            ex.printStackTrace();
        }

    }

}
