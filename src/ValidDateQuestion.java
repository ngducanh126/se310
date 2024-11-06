import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidDateQuestion extends Question {
    public ValidDateQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (Date format: YYYY-MM-DD)");
    }

    @Override
    public void take() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String response = inputHandler.getInput("Enter a date (YYYY-MM-DD): ");

        while (true) {
            try {
                LocalDate dateResponse = LocalDate.parse(response, formatter);
                responses.add(dateResponse.toString()); // Store in consistent format
                break;
            } catch (DateTimeParseException e) {
                response = inputHandler.getInput("Invalid date format. Please enter the date in YYYY-MM-DD format: ");
            }
        }
    }
    @Override
    public void editQuestion() {
        // Prompt to modify the question text
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
            outputHandler.displayMessage("Date question prompt modified successfully!");
        }
    }


}
