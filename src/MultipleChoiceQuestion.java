import java.util.ArrayList;
import java.util.HashSet;

public class MultipleChoiceQuestion extends Question {
    private ArrayList<String> choices;
    private int maxValidChoices;

    public MultipleChoiceQuestion(String questionText, ArrayList<String> choices, int maxValidChoices, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
        this.choices = choices;
        this.maxValidChoices = maxValidChoices;
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (Select up to " + maxValidChoices + " choices)");
        for (int i = 0; i < choices.size(); i++) {
            outputHandler.displayMessage((char) ('A' + i) + ") " + choices.get(i));
        }
    }

    @Override
    public void take() {
        outputHandler.displayMessage("You can select up to " + maxValidChoices + " unique choices.");

        int numSelections = 0;
        while (true) {
            try {
                numSelections = Integer.parseInt(inputHandler.getInput("Enter the number of choices you want to make (1 to " + maxValidChoices + "): "));
                if (numSelections > 0 && numSelections <= maxValidChoices) {
                    break;
                } else {
                    outputHandler.displayMessage("Please enter a number between 1 and " + maxValidChoices + ".");
                }
            } catch (NumberFormatException e) {
                outputHandler.displayMessage("Invalid input. Please enter a valid number.");
            }
        }

        HashSet<String> selectedChoices = new HashSet<>();
        for (int i = 0; i < numSelections; i++) {
            String response;
            while (true) {
                response = inputHandler.getInput("Select choice #" + (i + 1) + " (A, B, C, ...): ").toUpperCase();
                if (isValidChoice(response) && !selectedChoices.contains(response)) {
                    selectedChoices.add(response);
                    break;
                } else if (selectedChoices.contains(response)) {
                    outputHandler.displayMessage("You've already selected this choice. Please select a different choice.");
                } else {
                    outputHandler.displayMessage("Invalid choice. Please select a valid option.");
                }
            }
        }

        // Store the responses as a comma-separated string
        responses.add(String.join(", ", selectedChoices));
    }

    private boolean isValidChoice(String response) {
        if (response.length() == 1) {
            char choice = response.charAt(0);
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

                // Check if the input is a single valid letter within the choices range
                if (choiceToModify.length() == 1) {
                    int choiceIndex = choiceToModify.charAt(0) - 'A';
                    if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                        String newChoice = inputHandler.getInput("Enter the new value for choice " + choiceToModify + " (or leave blank to keep current): ");
                        if (!newChoice.isEmpty()) {
                            choices.set(choiceIndex, newChoice);
                            outputHandler.displayMessage("Choice " + choiceToModify + " modified successfully!");
                        }
                        continue;
                    }
                }

                // If the input is invalid, display an error message
                outputHandler.displayMessage("Invalid choice. Please enter a valid letter corresponding to the available options.");
            }
            outputHandler.displayMessage("Multiple-choice options modified successfully!");
        }
        // Ask the user if they want to modify the maximum number of allowed choices
        String modifyMaxChoices;
        while (true) {
            modifyMaxChoices = inputHandler.getInput("Do you want to modify the maximum number of allowed choices? (yes/no): ").toLowerCase();
            if (modifyMaxChoices.equals("yes") || modifyMaxChoices.equals("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyMaxChoices.equals("yes")) {
            while (true) {
                try {
                    int newMaxValidChoices = Integer.parseInt(inputHandler.getInput("Enter the maximum number of choices allowed : "));
                    if (newMaxValidChoices > 0 && newMaxValidChoices <= choices.size()) {
                        maxValidChoices = newMaxValidChoices;
                        outputHandler.displayMessage("Maximum number of allowed choices modified successfully!");
                        break;
                    } else {
                        outputHandler.displayMessage("Please enter a number between 1 and " + choices.size() + ".");
                    }
                } catch (NumberFormatException e) {
                    outputHandler.displayMessage("Invalid input. Please enter a valid number.");
                }
            }
        }

    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
