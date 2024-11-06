import java.io.Serializable;
import java.util.ArrayList;

public abstract class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String questionText;
    protected ArrayList<String> responses;
    protected OutputHandler outputHandler;
    protected InputHandler inputHandler;

    // Constructor with questionText parameter
    public Question(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        this.questionText = questionText;
        this.responses = new ArrayList<>();
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    // Abstract methods that subclasses must implement
    public abstract void displayQuestion();
    public abstract void take();
    public abstract void editQuestion(String newText);

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void saveQuestion(String filePath) {
        // Serialization logic here if needed
    }

    public static Question loadQuestion(String filePath) {
        // Deserialization logic here if needed
        return null;
    }
}