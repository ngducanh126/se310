public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);  // Pass questionText to superclass
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (True/False)");
    }

    @Override
    public void take() {
        String response = inputHandler.getInput("Please answer True or False: ");
        while (!response.equalsIgnoreCase("True") && !response.equalsIgnoreCase("False")) {
            response = inputHandler.getInput("Invalid input. Please answer True or False: ");
        }
        responses.add(response); // Store the response in the superclass's responses list
    }

    @Override
    public void editQuestion(String newText) {
        setQuestionText(newText);
    }
}
