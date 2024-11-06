import java.io.Serializable;

public abstract class OutputHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    // Abstract method to display a message to the user
    public abstract void displayMessage(String message);

    // Abstract method to log a message, potentially for debugging or auditing
    public abstract void logMessage(String message);
}
