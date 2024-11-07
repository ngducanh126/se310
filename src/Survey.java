import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surveyName;
    private List<Question> questions;
    private transient OutputHandler outputHandler; // Marked transient to avoid serialization
    private transient InputHandler inputHandler;   // Marked transient to avoid serialization

    public Survey(String surveyName, OutputHandler outputHandler, InputHandler inputHandler) {
        this.surveyName = surveyName;
        this.questions = new ArrayList<>();
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    // Adds a question to the survey
    public void addQuestion(Question question) {
        questions.add(question);
        outputHandler.displayMessage("Question added successfully!");
    }

    // Displays the survey and its questions
    public void displaySurvey() {
        outputHandler.displayMessage("\n--- Survey: " + surveyName + " ---\n");

        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions in this survey.");
            return;
        }

        int questionNumber = 1;
        for (Question question : questions) {
            // Display question number
            outputHandler.displayMessage(String.valueOf(questionNumber) +'.');

            // Display the question details (options, format, etc.)
            question.displayQuestion();

            // Add an empty line between each question for better readability
            outputHandler.displayMessage("");

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

        int questionIndex;
        try {
            questionIndex = Integer.parseInt(inputHandler.getInput("Enter question number: ")) - 1;
        } catch (NumberFormatException e) {
            outputHandler.displayMessage("Invalid input. Please enter a number.");
            return;
        }

        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            outputHandler.displayMessage("Current Question: ");
            question.displayQuestion();

            // Delegate the modification to the question’s edit() method
            question.editQuestion();
        } else {
            outputHandler.displayMessage("Invalid question number.");
        }
    }

    // Simulates taking the survey
    public Response takeSurvey() {
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions available in this survey.");
            return null;
        }

        Response response = new Response(outputHandler);
        outputHandler.displayMessage("Starting Survey: " + surveyName);

        for (Question question : questions) {
            question.displayQuestion(); // Display question prompt
            question.take(); // Take the input, with validation specific to each question type
            response.addResponse(question.getResponses().get(question.getResponses().size() - 1));
        }

        outputHandler.displayMessage("Survey completed.");
        return response;
    }

    // Saves the survey to a file
    public void saveSurvey(String dirPath, String fileName) {
    }

    // Loads the survey from a file
    public static Survey loadSurvey(String filePath) {
    }

    public String getName() {
        return surveyName;
    }

    public void setOutputHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
    public void setHandlers(OutputHandler outputHandler, InputHandler inputHandler) {
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;

        for (Question question : questions) {
            question.setOutputHandler(outputHandler);
            question.setInputHandler(inputHandler);
        }
    }
}
