public class EssayQuestion extends Question {

    public EssayQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText);
    }

    @Override
    public void take() {
        String response = inputHandler.getInput("Your essay response (you may write multiple paragraphs): ");
        responses.add(response);
    }

    @Override
    public void editQuestion() {
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
            outputHandler.displayMessage("Essay question prompt modified successfully!");
        }
    }
}
