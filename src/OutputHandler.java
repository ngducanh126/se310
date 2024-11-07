import java.io.Serializable;

public abstract class OutputHandler implements Serializable {
    private static final long serialVersionUID = 1L;
    public abstract void displayMessage(String message);
    public abstract void logMessage(String message);
}
