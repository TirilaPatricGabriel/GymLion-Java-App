package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String FILE_PATH = "logs/audit.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static AuditService instance;

    private AuditService() {
        // Initialize the file if necessary (e.g., write headers if the file is new)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Check if the file is empty and write the header
            File file = new File(FILE_PATH);
            if (file.length() == 0) {
                writer.write("action,timestamp");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void logAction(String actionName) {
        System.out.println("ACTION: " + actionName);
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = actionName + "," + timestamp;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            System.out.println("LOG ENTRY: " + logEntry);

            // Debug: Print absolute file path
            File file = new File(FILE_PATH);
            System.out.println("Absolute File Path: " + file.getAbsolutePath());

            // Check if file exists and is writable
            if (file.exists()) {
                System.out.println("File exists.");
                if (file.canWrite()) {
                    System.out.println("File is writable.");
                } else {
                    System.out.println("File is not writable.");
                }
            } else {
                System.out.println("File does not exist.");
            }

            writer.write(logEntry);
            writer.newLine();
            writer.flush();  // Ensure the buffer is flushed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AuditService auditService = AuditService.getInstance();
//        auditService.logAction("Application started");
//        auditService.logAction("User logged in");
//        auditService.logAction("Performed some action");
    }
}
