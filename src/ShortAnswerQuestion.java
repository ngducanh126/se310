public class ShortAnswerQuestion extends EssayQuestion {
    private int characterLimit;

    public ShortAnswerQuestion(String questionText, int characterLimit, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
        this.characterLimit = characterLimit;
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (Limit: " + characterLimit + " characters)");
    }

    @Override
    public void take() {
        String response = inputHandler.getInput("Your answer (up to " + characterLimit + " characters): ");
        while (response.length() > characterLimit) {
            response = inputHandler.getInput("Answer exceeds character limit. Please enter a shorter answer: ");
        }
        responses.add(response);
    }

    public int getCharacterLimit() {
        return characterLimit;
    }

    public void setCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
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
            outputHandler.displayMessage("Short answer question prompt modified successfully!");
        }

        // Prompt to modify the character limit
        String modifyLimit;
        while (true) {
            modifyLimit = inputHandler.getInput("Do you want to modify the character limit? (yes/no): ");
            if (modifyLimit.equalsIgnoreCase("yes") || modifyLimit.equalsIgnoreCase("no")) {
                break;
            }
            outputHandler.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (modifyLimit.equalsIgnoreCase("yes")) {
            while (true) {
                try {
                    int newLimit = Integer.parseInt(inputHandler.getInput("Enter the new character limit: "));
                    setCharacterLimit(newLimit);
                    outputHandler.displayMessage("Character limit modified successfully!");
                    break;
                } catch (NumberFormatException e) {
                    outputHandler.displayMessage("Invalid input. Please enter a valid integer for the character limit.");
                }
            }
        }
    }

}
