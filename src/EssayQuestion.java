public class EssayQuestion extends Question {

    public EssayQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (Essay Question. You can write many paragraphs)");
    }

    @Override
    public void take() {
        outputHandler.displayMessage("Your essay response (press Enter after each paragraph).");

        while (true) {
            String response = inputHandler.getInput("> ");
            responses.add(response);

            String continueResponse;
            while (true) {
                continueResponse = inputHandler.getInput("Do you want to add another paragraph? (yes/no): ");
                if (continueResponse.equalsIgnoreCase("yes") || continueResponse.equalsIgnoreCase("no")) {
                    break;
                }
                outputHandler.displayMessage("Invalid input. Please type 'yes' or 'no'.");
            }

            if (continueResponse.equalsIgnoreCase("no")) {
                outputHandler.displayMessage("Essay responses saved.");
                break;
            }
        }
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
