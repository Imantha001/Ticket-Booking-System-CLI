import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private final String filePath; // File path to the log file

    public Logger(String filePath) {
        this.filePath = filePath; // Initialize the file path
    }

    // Method to log messages to the console and a file
    public synchronized void log(String message) {
        // Get the current timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = "[" + timestamp + "] " + message; // Log message with timestamp

        // Print the log message to the console
        System.out.println(logMessage);


        // Write the log message to the file
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Failed to log message: " + e.getMessage()); // Handle file write error
        }
    }
}
