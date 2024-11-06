import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surveyName;
    private List<Question> questions;
    private OutputHandler outputHandler;

    public Survey(String surveyName, OutputHandler outputHandler) {
        this.surveyName = surveyName;
        this.questions = new ArrayList<>();
        this.outputHandler = outputHandler;
    }

    // Adds a question to the survey
    public void addQuestion(Question question) {
        questions.add(question);
        outputHandler.displayMessage("Question added successfully!");
    }

    // Displays the survey and its questions
    public void displaySurvey() {
        outputHandler.displayMessage("\nSurvey: " + surveyName);
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions in this survey.");
            return;
        }

        int questionNumber = 1;
        for (Question question : questions) {
            outputHandler.displayMessage(questionNumber + ". ");
            question.displayQuestion();
            questionNumber++;
        }
    }

    // Modifies an existing question in the survey
    public void modifySurvey() {
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions available to modify.");
            return;
        }

        outputHandler.displayMessage("Select the question number you want to modify:");
        displaySurvey();

        int questionIndex = Integer.parseInt(new ConsoleInput().getInput("Enter question number: ")) - 1;

        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            outputHandler.displayMessage("Current Question: ");
            question.displayQuestion();
            question.editQuestion("Please enter new text for the question:");
            outputHandler.displayMessage("Question modified successfully!");
        } else {
            outputHandler.displayMessage("Invalid question number.");
        }
    }

    // Simulates taking the survey
    public void takeSurvey() {
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions available in this survey.");
            return;
        }

        outputHandler.displayMessage("Starting Survey: " + surveyName);
        for (Question question : questions) {
            question.take();
        }
        outputHandler.displayMessage("Survey completed. Thank you for your responses!");
    }

    // Saves the survey to a file
    public void saveSurvey(String filePath) {
        SerializeHelper.serialize(Survey.class, this, "", filePath);
        outputHandler.displayMessage("Survey saved to " + filePath);
    }

    // Loads the survey from a file
    public static Survey loadSurvey(String filePath) {
        Survey loadedSurvey = SerializeHelper.deserialize(Survey.class, filePath);
        return loadedSurvey;
    }

    public String getName() {
        return surveyName;
    }
}
