public class ConsoleOutput extends OutputHandler {
    private static final long serialVersionUID = 1L;

    // Constructor for ConsoleOutput
    public ConsoleOutput() {
        // No specific initialization needed for console output
    }

    // Implementation of displayMessage to print a message to the console
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Implementation of logMessage to print a log message to the console
    @Override
    public void logMessage(String message) {
        System.out.println("LOG: " + message);
    }
}
