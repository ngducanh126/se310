public class ShortAnswerQuestion extends Question {
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

    @Override
    public void editQuestion(String newText) {
        setQuestionText(newText);
    }

    public int getCharacterLimit() {
        return characterLimit;
    }

    public void setCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
    }
}
