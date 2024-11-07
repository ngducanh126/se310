import java.util.ArrayList;
import java.util.Arrays;

public class TrueFalseQuestion extends MultipleChoiceQuestion {

    public TrueFalseQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, new ArrayList<>(Arrays.asList("True", "False")), 1, outputHandler, inputHandler);
    }

    @Override
    public void displayQuestion() {
        // Display the question with True/False options
        outputHandler.displayMessage(questionText + " (True or False Question)");
    }

    @Override
    public void take() {
        String response = inputHandler.getInput("Please answer True or False: ");
        while (!response.equalsIgnoreCase("True") && !response.equalsIgnoreCase("False")) {
            response = inputHandler.getInput("Invalid input. Please answer True or False: ");
        }
        responses.add(response); // Store the response
    }

    @Override
    public void editQuestion() {
        // Since choices for True/False are fixed, only allow modifying the question text
        String modifyPrompt;
        while (true) {
            modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
            if (modifyPrompt.equalsIgnoreCase("yes") || modifyPrompt.equalsIgnoreCase("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyPrompt.equalsIgnoreCase("yes")) {
            String newPrompt = inputHandler.getInput("Enter the new prompt: ");
            setQuestionText(newPrompt);
            outputHandler.displayMessage("True/False question prompt modified successfully!");
        }
    }
}
