import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question {
    private ArrayList<String> choices;

    public MultipleChoiceQuestion(String questionText, ArrayList<String> choices, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
        this.choices = choices;
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText);
        for (int i = 0; i < choices.size(); i++) {
            outputHandler.displayMessage((char) ('A' + i) + ") " + choices.get(i));
        }
    }

    @Override
    public void take() {
        String response = inputHandler.getInput("Select an option (A, B, C, ...): ");
        while (!isValidChoice(response)) {
            response = inputHandler.getInput("Invalid option. Please select a valid choice: ");
        }
        responses.add(response);
    }

    private boolean isValidChoice(String response) {
        if (response.length() == 1) {
            char choice = response.toUpperCase().charAt(0);
            return choice >= 'A' && choice < 'A' + choices.size();
        }
        return false;
    }

    @Override
    public void editQuestion(String newText) {
        setQuestionText(newText);
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
