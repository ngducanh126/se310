import java.util.ArrayList;

public class MatchingQuestion extends Question {
    private ArrayList<String> leftItems;
    private ArrayList<String> rightItems;

    public MatchingQuestion(String questionText, ArrayList<String> leftItems, ArrayList<String> rightItems, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
        this.leftItems = leftItems;
        this.rightItems = rightItems;
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText);
        for (int i = 0; i < leftItems.size(); i++) {
            outputHandler.displayMessage((char) ('A' + i) + ") " + leftItems.get(i) + " <-> " + (i + 1) + ". " + rightItems.get(i));
        }
    }

    @Override
    public void take() {
        for (int i = 0; i < leftItems.size(); i++) {
            String response = inputHandler.getInput("Match each left option to a right option in the format 'left_option-right_option': ");

            // Validate that the response is in the correct format and matches items in leftItems and rightItems
            while (!isValidFormat(response)) {
                response = inputHandler.getInput("Invalid format. Please enter the match in 'left_option-right_option' format with valid options: ");
            }
            // If the format is correct, split the input and validate against the lists
            String[] parts = response.split("-", 2);
            String leftOption = parts[0].trim();
            String rightOption = parts[1].trim();

            responses.add(response); // Add the validated response

            // Display success message showing the matched items
            outputHandler.displayMessage("Successfully matched: " + leftOption + " with " + rightOption);
        }
    }

    // Helper method to validate the input format
    private boolean isValidFormat(String response) {
        // Check if the response contains exactly one hyphen separating two non-empty parts
        if (response == null || !response.contains("-")) {
            return false;
        }

        String[] parts = response.split("-", 2);
        String leftOption = parts[0].trim();
        String rightOption = parts[1].trim();

        // Check if the left part exists in leftItems and the right part exists in rightItems
        return leftItems.contains(leftOption) && rightItems.contains(rightOption);
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
            outputHandler.displayMessage("Matching question prompt modified successfully!");
        }

        // Prompt to modify the left items
        String modifyLeftItems;
        while (true) {
            modifyLeftItems = inputHandler.getInput("Do you want to modify the left items? (yes/no): ");
            if (modifyLeftItems.equalsIgnoreCase("yes") || modifyLeftItems.equalsIgnoreCase("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyLeftItems.equalsIgnoreCase("yes")) {
            for (int i = 0; i < leftItems.size(); i++) {
                outputHandler.displayMessage("Current left item " + (i + 1) + ": " + leftItems.get(i));
                String newLeftItem = inputHandler.getInput("Enter new value for left item " + (i + 1) + " (or leave blank to keep current): ");
                if (!newLeftItem.isEmpty()) {
                    leftItems.set(i, newLeftItem);
                }
            }
            outputHandler.displayMessage("Left items modified successfully!");
        }

        // Prompt to modify the right items
        String modifyRightItems;
        while (true) {
            modifyRightItems = inputHandler.getInput("Do you want to modify the right items? (yes/no): ");
            if (modifyRightItems.equalsIgnoreCase("yes") || modifyRightItems.equalsIgnoreCase("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyRightItems.equalsIgnoreCase("yes")) {
            for (int i = 0; i < rightItems.size(); i++) {
                outputHandler.displayMessage("Current right item " + (i + 1) + ": " + rightItems.get(i));
                String newRightItem = inputHandler.getInput("Enter new value for right item " + (i + 1) + " (or leave blank to keep current): ");
                if (!newRightItem.isEmpty()) {
                    rightItems.set(i, newRightItem);
                }
            }
            outputHandler.displayMessage("Right items modified successfully!");
        }
    }


    public ArrayList<String> getLeftItems() {
        return leftItems;
    }

    public ArrayList<String> getRightItems() {
        return rightItems;
    }

    public void setMatchingItems(ArrayList<String> leftItems, ArrayList<String> rightItems) {
        this.leftItems = leftItems;
        this.rightItems = rightItems;
    }
}
