/**
 * Lesson3Streams.java
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Lesson3Streams {

    public static void main(String[] args) {

        int i = 1;
        String text = null, filename = null;
        List<String> words;
        long beforeStream, afterStream, beforeParallelStream, afterParallelStream, timeDiff = 0, matchCount = 0;

        // Get the file name from the command line...
        for (String s : args) {
            filename = s;
        }

        // ...or use a default name.
        if (filename == null)
            filename = "JobResult_124432.txt";

        // Read the file into a string
        try {
            text = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create a loop to compare stream vs. parallelStream until parallelStream is faster
        while (timeDiff <= 0.0) {

            // split the string into "words"
            words = Arrays.asList(text.split(","));

            // Print basic info about this test run
            System.out.println("Try " + i++ + ": ");
            System.out.println("String Size: " + text.length());

            // Test how fast stream is:
            beforeStream = System.currentTimeMillis();
            matchCount = words.stream().filter(s -> s.matches(".*00000000[0-9A-Fa-f]{8}.*")).count();
            afterStream = System.currentTimeMillis();

            // Print the results:
            // System.out.println("Match Count: " + matchCount);
            System.out.println("Millisecs using stream: " + (afterStream - beforeStream));

            // Test how fast parallelStream is:
            beforeParallelStream = System.currentTimeMillis();
            matchCount = words.parallelStream().filter(s -> s.matches(".*00000000[0-9A-Fa-f]{8}.*")).count();
            afterParallelStream = System.currentTimeMillis();

            // Print the results:
            // System.out.println("Match Count: " + matchCount);
            System.out.println("Millisecs using parallelStream: " + (afterParallelStream - beforeParallelStream));

            // Find out the difference between the two runs
            timeDiff = (afterStream - beforeStream) - (afterParallelStream - beforeParallelStream);

            // Only do this is stream is faster than parallelStream
            if (timeDiff <= 0.0) {
                System.out.println(
                        "Results: stream was " + (timeDiff * -1) + " milliseconds faster than parallelStream.");

                System.out.println("Doubling String size and trying again");
                text = text + text;
            }
        }

        // Print the final results
        System.out.println("Results: paralleStream was " + timeDiff + " milliseconds faster than stream.");
        System.out.println("All done!");

    }

}
