import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class InvalidPathException extends Exception {
    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class LogManager {
    private BufferedWriter writer;
    private File currentLogFile;
    private String baseFolder;            // For storing base folder name like "AGV", "Battery", etc.

    public LogManager(String folderName) {
        this.baseFolder = folderName.trim();
    }

    // Initialize and create folder structure for logs
    public void initializeLog() throws InvalidPathException {
        try {

            if (baseFolder == null || baseFolder.isEmpty()) {
                throw new InvalidPathException("Base folder name is empty or null.");
            }

            Date now = new Date();

            // Format year, month, date, time for folder and file
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");

            String year = yearFormat.format(now);
            String month = monthFormat.format(now);
            String date = dateFormat.format(now);
            String time = timeFormat.format(now);

            // Folder structure: logs/year/month/date/
            String folderPath = "Logs/" + baseFolder + "/" + year + "/" + month + "/" + date;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (!created && !folder.exists()) {
                    throw new InvalidPathException("Failed to create folder path: " + folderPath);
                }
                System.out.println("[LOG] Created folders: " + folderPath);
            }

            // Create timestamped log file
            String fileName = "log_" + time + ".txt";
            File logFile = new File(folderPath, fileName);
            try {
                boolean fileCreated = logFile.createNewFile();
                // If file cannot be created, throw InvalidPathException
                if (!fileCreated && !logFile.exists()) {
                    throw new InvalidPathException("Failed to create log file: " + logFile.getAbsolutePath());
                }
            } catch (IOException ioe) {
                // Wrap and re-throw as InvalidPathException (user-defined)
                throw new InvalidPathException("I/O error while creating log file: " + ioe.getMessage(), ioe);
            }

            currentLogFile = logFile; //Assign to currentLogFile so archiveLog()

            // Open writer
            try {
                writer = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException ioe) {
                // wrap and rethrow
                throw new InvalidPathException("I/O error while opening log writer: " + ioe.getMessage(), ioe);
            }

            log("[SYSTEM] Log initialized at " + now);
            System.out.println("[LOG] Log file created: " + logFile.getAbsolutePath());

        } catch (InvalidPathException ipe) {
            // rethrow user-defined exception unchanged
            throw ipe;
        } catch (Exception e) {
            // In case any unexpected runtime exception occurs, wrap in InvalidPathException
            throw new InvalidPathException("Unexpected error initializing log: " + e.getMessage(), e);
        }
    }

    // Write a log entry with timestamp
    public void log(String message) {
        try {
            if (writer == null) initializeLog();
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            writer.write("[" + timestamp + "] " + message + "\n");
            writer.flush();
        } catch (IOException | InvalidPathException e) {
            System.err.println("Error writing to log: " + e.getMessage());
        }
    }

    // Close the log safely
    public void closeLog() {
        try {
            if (writer != null) {
                log("[SYSTEM] Log closed.");
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing log: " + e.getMessage());
        }
    }

    // Archive current log file
    public void archiveLog() {
        if (currentLogFile == null) {
            System.out.println("No log file to archive.");
            return;
        }

        try {
            // Extract year/month/date from currentLogFile path
            File parent = currentLogFile.getParentFile(); // points to date folder
            File monthFolder = parent.getParentFile();
            File yearFolder = monthFolder.getParentFile();

            // Create same structure inside Archive
            String archiveFolderPath = "Logs/" + baseFolder + "/Archive/" + yearFolder.getName() + "/" + monthFolder.getName() + "/" + parent.getName();
            File archiveDir = new File(archiveFolderPath);
            if (!archiveDir.exists()) archiveDir.mkdirs();

            File destFile = new File(archiveDir, currentLogFile.getName());

            try (FileInputStream in = new FileInputStream(currentLogFile);
                 FileOutputStream out = new FileOutputStream(destFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

            System.out.println("[LOG] Archived file to: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error archiving log: " + e.getMessage());
        }
    }

    // to view a specific log file
    public void viewLog(String baseFolder, String year, String month, String date, String fileName) throws InvalidPathException {
        try {
            if (!baseFolder.equals("AGV") && !baseFolder.equals("Battery")
                    && !baseFolder.equals("Overall") && !baseFolder.equals("System")) {
                throw new InvalidPathException("Invalid base folder! Try again.");
            }
            if (!year.matches("\\d{4}")) throw new InvalidPathException("Year must be in yyyy format.");
            if (!month.matches("[A-Za-z]{3}")) throw new InvalidPathException("Month must be in MMM format.");
            if (!date.matches("\\d{1,2}")) throw new InvalidPathException("Date must be numeric.");
            if (!fileName.matches("log_\\d{2}-\\d{2}-\\d{2}\\.txt"))
                throw new InvalidPathException("File name must be in log_HH-mm-ss.txt format.");

            File logFolder = new File("Logs/" + baseFolder + "/" + year + "/" + month + "/" + date + "/" + fileName);

            if (!logFolder.exists()) {
                throw new InvalidPathException("File not found. Try again.");
            }

            // If everything is valid, open file
            Desktop.getDesktop().open(logFolder);
            System.out.println("[LOG] Opened file: " + logFolder.getAbsolutePath());

        } catch (InvalidPathException | IOException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    // to delete a specific log file
    public void deleteLog(String baseFolder, String year, String month, String date, String fileName) throws InvalidPathException {
        try {
            if (!baseFolder.equals("AGV") && !baseFolder.equals("Battery")
                    && !baseFolder.equals("Overall") && !baseFolder.equals("System")) {
                throw new InvalidPathException("Invalid base folder! Try again.");
            }
            if (!year.matches("\\d{4}")) throw new InvalidPathException("Year must be in yyyy format.");
            if (!month.matches("[A-Za-z]{3}")) throw new InvalidPathException("Month must be in MMM format.");
            if (!date.matches("\\d{1,2}")) throw new InvalidPathException("Date must be numeric.");
            if (!fileName.matches("log_\\d{2}-\\d{2}-\\d{2}\\.txt"))
                throw new InvalidPathException("File name must be in log_HH-mm-ss.txt format.");

            File logFile = new File("Logs/" + baseFolder + "/" + year + "/" + month + "/" + date + "/" + fileName);

            if (!logFile.exists()) {
                throw new InvalidPathException("File not found. Try again.");
            }

            // Throw exception if file deletion fails
            if (!logFile.delete()) {
                throw new InvalidPathException("Failed to delete file: " + logFile.getAbsolutePath());
            } else {
                System.out.println("[LOG] File deleted successfully: " + logFile.getAbsolutePath());
            }

        } catch (InvalidPathException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    // to move a specific log file to the user enter destination path
    public void moveLog(String baseFolder, String year, String month, String date, String fileName, String destinationPath) throws InvalidPathException {
        try {
            File sourceFile = new File("Logs/" + baseFolder + "/" + year + "/" + month + "/" + date + "/" + fileName);

            if (!sourceFile.exists()) {
                System.err.println("[ERROR] Source file not found: " + sourceFile.getAbsolutePath());
                return;
            }

            // Create destination folder if not exists
            File destinationFolder = new File(destinationPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
                System.out.println("[LOG] Created destination folder: " + destinationFolder.getAbsolutePath());
            }

            File destinationFile = new File(destinationFolder, fileName);
            try (FileInputStream in = new FileInputStream(sourceFile);
                 FileOutputStream out = new FileOutputStream(destinationFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

            } catch (IOException e) {
                System.err.println("Error moving file: " + e.getMessage());
                return;
            }

            // Delete after streams are fully closed
            if (sourceFile.delete()) {
                System.out.println("[LOG] File moved successfully to: " + destinationFile.getAbsolutePath());
            } else {
                System.err.println("[WARN] File copied but not deleted from source.");
            }
        } catch (Exception ex) {
            System.err.println("[ERROR] Unexpected error: " + ex.getMessage());
        }
    }
}