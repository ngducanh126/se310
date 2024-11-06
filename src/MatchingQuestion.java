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
            String response = inputHandler.getInput("Match " + leftItems.get(i) + " with the number from right items: ");
            responses.add(response);
        }
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
