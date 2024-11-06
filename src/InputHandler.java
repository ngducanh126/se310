import java.io.Serializable;

public abstract class InputHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract String getInput(String prompt);
}
