package flashcards;

import java.util.ArrayList;
import java.util.Scanner;

public class LoggedIO {

    public static final LoggedInput in = new LoggedInput();
    public static final LoggedOutput out = new LoggedOutput();
    private static final ArrayList<String> logBuffer = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    private LoggedIO() {
    }

    public static String getLog() {
        return String.join("\n", logBuffer);
    }

    public static class LoggedInput {
        public String nextLine() {
            String line = scanner.nextLine().trim();
            logBuffer.add(line);
            return line;
        }
    }

    public static class LoggedOutput {
        public void printf(String format, Object... args) {
            String line = String.format(format, args);
            logBuffer.add(line);
            System.out.print(line);
        }

        public void println(String line) {
            logBuffer.add(line);
            System.out.println(line);
        }
    }
}
