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
    public void editQuestion(String newText) {
        setQuestionText(newText);
    }
}
