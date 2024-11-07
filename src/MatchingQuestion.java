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
        outputHandler.displayMessage(questionText+" (Matching Question)");

        // Find the maximum size for left and right items to handle varying lengths.
        int leftSize = leftItems.size();
        int rightSize = rightItems.size();
        int maxItems = Math.max(leftSize, rightSize);

        StringBuilder leftColumn = new StringBuilder();
        StringBuilder rightColumn = new StringBuilder();

        // Build each column separately
        for (int i = 0; i < maxItems; i++) {
            // Add left item with label (e.g., A), if available
            if (i < leftSize) {
                leftColumn.append((char) ('A' + i)).append(") ").append(leftItems.get(i));
            } else {
                leftColumn.append("     "); // Padding for missing left items if right list is longer
            }
            leftColumn.append("\n"); // New line after each left item

            // Add right item with label (e.g., 1), if available
            if (i < rightSize) {
                rightColumn.append((i + 1)).append(") ").append(rightItems.get(i));
            }
            rightColumn.append("\n"); // New line after each right item
        }

        // Output the columns side by side by splitting into lines and combining them
        String[] leftLines = leftColumn.toString().split("\n");
        String[] rightLines = rightColumn.toString().split("\n");

        // Display both columns in the aligned format
        for (int i = 0; i < maxItems; i++) {
            outputHandler.displayMessage(
                    String.format("%-20s %s", leftLines[i], i < rightLines.length ? rightLines[i] : "")
            );
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
