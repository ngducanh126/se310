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
            outputHandler.displayMessage("Multiple-choice question prompt modified successfully!");
        }

        // Prompt to modify the choices
        String modifyChoices;
        while (true) {
            modifyChoices = inputHandler.getInput("Do you want to modify the choices? (yes/no): ");
            if (modifyChoices.equalsIgnoreCase("yes") || modifyChoices.equalsIgnoreCase("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyChoices.equalsIgnoreCase("yes")) {
            // Display all choices
            outputHandler.displayMessage("Current choices:");
            for (int i = 0; i < choices.size(); i++) {
                outputHandler.displayMessage((char) ('A' + i) + ") " + choices.get(i));
            }

            while (true) {
                // Ask the user which choice they want to modify
                String choiceToModify = inputHandler.getInput("Enter the letter of the choice you want to modify (or 'done' to finish): ").toUpperCase();
                if (choiceToModify.equals("DONE")) {
                    break;
                }

                // Check if the input is a valid choice
                int choiceIndex = choiceToModify.charAt(0) - 'A';
                if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                    String newChoice = inputHandler.getInput("Enter the new value for choice " + choiceToModify + " (or leave blank to keep current): ");
                    if (!newChoice.isEmpty()) {
                        choices.set(choiceIndex, newChoice);
                        outputHandler.displayMessage("Choice " + choiceToModify + " modified successfully!");
                    }
                } else {
                    outputHandler.displayMessage("Invalid choice. Please enter a valid letter.");
                }
            }
            outputHandler.displayMessage("Multiple-choice options modified successfully!");
        }
    }


    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public ArrayList<String> getOptions() {
        return choices;
    }

    public void setOptions(ArrayList<String> choices) {
        this.choices = choices;
    }
}
