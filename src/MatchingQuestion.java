import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        HashSet<String> usedRightOptions = new HashSet<>(); // Track used right options
        HashSet<String> matchedLeftOptions = new HashSet<>(); // Track matched left options

        outputHandler.displayMessage("Match each left option to a right option in the format 'A 1' or 'B 2'.");

        while (matchedLeftOptions.size() < leftItems.size()) {
            // Prompt the user for a match
            String response = inputHandler.getInput("Enter a match in 'A 1' format, or type 'done' if finished: ").trim();

            // Check if the user is finished
            if (response.equalsIgnoreCase("done")) {
                break;
            }

            // Validate the format and check the match
            if (!isValidFormat(response)) {
                outputHandler.displayMessage("Invalid format. Please enter in 'A 1' format with valid options.");
                continue;
            }

            // Parse the left and right parts
            String[] parts = response.split(" ");
            String leftIndex = parts[0].trim();
            String rightIndex = parts[1].trim();

            // Convert indices to actual left and right items
            int leftPos = leftIndex.charAt(0) - 'A';
            int rightPos;

            try {
                rightPos = Integer.parseInt(rightIndex) - 1;
            } catch (NumberFormatException e) {
                outputHandler.displayMessage("Invalid number for the right option. Please try again.");
                continue;
            }

            // Check if indices are within bounds and that left/right items haven't been used
            if (leftPos < 0 || leftPos >= leftItems.size() || rightPos < 0 || rightPos >= rightItems.size()) {
                outputHandler.displayMessage("Selected options are out of bounds. Please enter valid options.");
                continue;
            }

            String leftOption = leftItems.get(leftPos);
            String rightOption = rightItems.get(rightPos);

            if (matchedLeftOptions.contains(leftOption)) {
                outputHandler.displayMessage("This left option has already been matched. Please select another.");
                continue;
            }

            if (usedRightOptions.contains(rightOption)) {
                outputHandler.displayMessage("This right option has already been used. Please select another.");
                continue;
            }

            // Add the match to responses and mark items as used
            responses.add(leftOption + " - " + rightOption);
            matchedLeftOptions.add(leftOption);
            usedRightOptions.add(rightOption);

            // Display success message
            outputHandler.displayMessage("Successfully matched: ");
        }

        // Final check if any left items are unmatched
        if (matchedLeftOptions.size() < leftItems.size()) {
            outputHandler.displayMessage("Warning: Not all left items have been matched.");
        } else {
            outputHandler.displayMessage("All matches have been successfully made.");
        }
    }

    // Helper method to validate the input format
    private boolean isValidFormat(String response) {
        // Ensure format is 'A 1' (left option letter followed by right option number)
        if (response == null || response.trim().isEmpty() || !response.contains(" ")) {
            return false;
        }

        String[] parts = response.split(" ");
        if (parts.length != 2) {
            return false;
        }

        String leftPart = parts[0].trim();
        String rightPart = parts[1].trim();

        // Check if left part is a single letter within valid range, and right part is a number
        return leftPart.length() == 1 && leftPart.charAt(0) >= 'A' && leftPart.charAt(0) < 'A' + leftItems.size()
                && rightPart.matches("\\d+");
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

        // Prompt to modify left items
        String modifyLeftItems = inputHandler.getInput("Do you want to modify the left items? (yes/no): ");
        if (modifyLeftItems.equalsIgnoreCase("yes")) {
            modifyItems(leftItems, "left");
        }

        // Prompt to modify right items
        String modifyRightItems = inputHandler.getInput("Do you want to modify the right items? (yes/no): ");
        if (modifyRightItems.equalsIgnoreCase("yes")) {
            modifyItems(rightItems, "right");
        }
    }

    // Helper method to modify specified items in a list (left or right)
    private void modifyItems(ArrayList<String> items, String side) {
        outputHandler.displayMessage("Current " + side + " items:");
        for (int i = 0; i < items.size(); i++) {
            outputHandler.displayMessage((char) ('A' + i) + ") " + items.get(i));
        }

        // Prompt for items to modify
        List<Integer> indicesToModify = getIndicesToModify(items.size(), side);
        for (int index : indicesToModify) {
            String currentItem = items.get(index);
            outputHandler.displayMessage("Current " + side + " item " + (char) ('A' + index) + ": " + currentItem);
            String newItem = inputHandler.getInput("Enter new value for " + side + " item " + (char) ('A' + index) + " : ");
            if (!newItem.isEmpty()) {
                items.set(index, newItem);
                outputHandler.displayMessage(side + " item " + (char) ('A' + index) + " modified successfully!");
            }
        }
    }

    // Method to get indices to modify based on user input in 'A B C' format
    private List<Integer> getIndicesToModify(int itemCount, String side) {
        List<Integer> indicesToModify = new ArrayList<>();
        String validOptions = generateValidOptions(itemCount);

        while (true) {
            String input = inputHandler.getInput("Enter the letters of the " + side + " items you want to modify seperated by a white space ").trim().toUpperCase();
            String[] selectedItems = input.split("\\s+");

            boolean isValid = true;
            for (String item : selectedItems) {
                if (item.length() != 1 || validOptions.indexOf(item.charAt(0)) == -1) {
                    isValid = false;
                    break;
                } else {
                    int index = item.charAt(0) - 'A';
                    if (!indicesToModify.contains(index)) {
                        indicesToModify.add(index);
                    }
                }
            }

            if (isValid) {
                break;
            } else {
                outputHandler.displayMessage("Invalid input. Please enter valid letters separated by spaces (e.g., A B C).");
            }
        }
        return indicesToModify;
    }

    // Generate valid options based on the number of items
    private String generateValidOptions(int itemCount) {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < itemCount; i++) {
            options.append((char) ('A' + i));
        }
        return options.toString();
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
