
/**
 * Lesson1IOStream.java
 * 
 * @author Bryan Glogowski
 * 
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Lesson1IOStream {

    // List of all Employee Objects
    static List<Employee> employees = new LinkedList<Employee>();

    // File name constants to use for each I/O type
    private static final String BINFILE = "data.bin";
    private static final String TXTFILE = "data.txt";
    private static final String OBJFILE = "data.obj";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        boolean help = false;
        boolean text = false;
        boolean binary = false;
        boolean object = false;

        int i = 0;
        String arg;

        Scanner scanner = new Scanner(System.in);

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            // Users can select multiple options at runtime...
            // ...and developers can test multiple solutions.

            if (arg.endsWith("help"))
                help = true;
            if (arg.endsWith("text"))
                text = true;
            if (arg.endsWith("binary"))
                binary = true;
            if (arg.endsWith("object"))
                object = true;
        }

        if (binary)
            readBinaryFile(BINFILE);

        if (text)
            readTextFile(TXTFILE);

        if (object)
            readObjectFile(OBJFILE);

        if (help)
            printHelp();

        System.out.println("Input User Info (Use the format - [Name]|[Salary]|[Hire Date: YYYY-M-D]):");
        employees.add(getEmployeeConsole(scanner));

        System.out.println();
        printEmployeeConsole();

        if (binary)
            writeBinaryFile(BINFILE);

        if (text)
            writeTextFile(TXTFILE);

        if (object)
            writeObjectFile(OBJFILE);

    }

    // Console methods

    private static Employee getEmployeeConsole(Scanner in) {

        // Set the format so users can input simplified dates
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-M-d");

        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);

        LocalDate hireDate = LocalDate.parse(tokens[2], format);
        int year = hireDate.getYear();
        int month = hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();

        return new Employee(name, salary, year, month, day);
    }

    private static void printEmployeeConsole() {

        System.out.println("List of current employees:");
        for (Employee e : employees) {
            System.out.print(e.toString());
        }

    }

    // Text I/O methods

    private static void writeTextFile(String filename) {

        try {
            FileWriter fileWriter = new FileWriter(filename);

            for (Employee e : employees) {
                fileWriter.write(e.toString());
            }
            fileWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void readTextFile(String filename) {

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                employees.add(readEmployeeData(line));
            }
            fileReader.close();

        } catch (FileNotFoundException fnf) {
            // This can safely be ignored for this assignment
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Binary I/O methods

    private static void writeBinaryFile(String filename) throws IOException {
        FileOutputStream stream = getFileOutputStream(filename);

        try {
            for (Employee e : employees) {
                stream.write(e.toString().getBytes(StandardCharsets.UTF_8));
            }
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readBinaryFile(String filename) throws IOException {

        File file = new File(filename);

        if (file.exists()) {
            FileInputStream fileStream = new FileInputStream(file);

            byte[] bytes = new byte[(int) file.length()];
            fileStream.read(bytes);
            fileStream.close();

            String s = new String(bytes);
            String[] lines = s.split("\\r?\\n");

            for (String line : lines) {
                employees.add(readEmployeeData(line));
            }
        }

    }

    // Object I/O methods

    private static void writeObjectFile(String filename) {
        FileOutputStream fileStream = getFileOutputStream(filename);

        try {
            ObjectOutputStream stream = new ObjectOutputStream(fileStream);

            for (Employee e : employees) {
                stream.writeObject(e);
            }
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void readObjectFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileStream = getFileInputStream(filename);

        try {
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            while (true) {
                try {
                    employees.add((Employee) objectStream.readObject());
                } catch (EOFException ex) {
                    break;
                }
            }
            objectStream.close();
        } catch (NullPointerException ex) {
            // This can safely be ignored for this assignment
        }
    }

    // Methods to create File I/O streams

    private static FileOutputStream getFileOutputStream(String filename) {
        File file = new File(filename);
        FileOutputStream stream = null;

        try {
            if (file.isDirectory()) {
                throw new FileNotFoundException();
            } else {
                stream = new FileOutputStream(file);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return stream;
    }

    private static FileInputStream getFileInputStream(String filename) {
        File file = new File(filename);
        FileInputStream stream = null;

        try {
            if (file.isDirectory()) {
                throw new FileNotFoundException();
            } else {
                stream = new FileInputStream(file);
            }
        } catch (FileNotFoundException ex) {
            // This can safely be ignored for this assignment
        }

        return stream;
    }

    // Method to convert string data to an Employee Object

    private static Employee readEmployeeData(String s) {

        // Set the format so users can input simplified dates
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-M-d");

        String[] tokens = s.replace("\n", "").replace("\r", "").split("\\|");

        String name = tokens[0];

        double salary = Double.parseDouble(tokens[1]);

        LocalDate hireDate = LocalDate.parse(tokens[2], format);
        int year = hireDate.getYear();
        int month = hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();

        return new Employee(name, salary, year, month, day);
    }

    // Method to print the help text

    public static void printHelp() {
        System.err.println("List of command-line options:");
        System.err.println("  --help\tdisplays help info\n"
                + "  --text\twrites/reads as text file and displays results on console\n"
                + "  --binary\twrites/reads as binary file and displays results on console\n"
                + "  --object\twrites/reads as object file and displays results on console");
    }

}
