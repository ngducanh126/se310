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
    public void editQuestion(String newText) {
        setQuestionText(newText);
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
