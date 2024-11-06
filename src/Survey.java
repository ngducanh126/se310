import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surveyName;
    private List<Question> questions;
    private transient OutputHandler outputHandler; // Marked transient to avoid serialization
    private transient InputHandler inputHandler;   // Marked transient to avoid serialization

    public Survey(String surveyName, OutputHandler outputHandler, InputHandler inputHandler) {
        this.surveyName = surveyName;
        this.questions = new ArrayList<>();
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    // Adds a question to the survey
    public void addQuestion(Question question) {
        questions.add(question);
        outputHandler.displayMessage("Question added successfully!");
    }

    // Displays the survey and its questions
    public void displaySurvey() {
        outputHandler.displayMessage("\nSurvey: " + surveyName);
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions in this survey.");
            return;
        }

        int questionNumber = 1;
        for (Question question : questions) {
            outputHandler.displayMessage(questionNumber + ". ");
            question.displayQuestion();
            questionNumber++;
        }
    }

    // Modifies an existing question in the survey
    public void modifySurvey() {
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions available to modify.");
            return;
        }

        outputHandler.displayMessage("Select the question number you want to modify:");
        displaySurvey();

        int questionIndex = Integer.parseInt(inputHandler.getInput("Enter question number: ")) - 1;

        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            outputHandler.displayMessage("Current Question: ");
            question.displayQuestion();

            // Check if the question is an EssayQuestion
            if (question instanceof EssayQuestion) {
                EssayQuestion essayQuestion = (EssayQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    essayQuestion.editQuestion(newPrompt);
                    outputHandler.displayMessage("Essay question prompt modified successfully!");
                }
            }
            // Check if the question is a TrueFalseQuestion
            else if (question instanceof TrueFalseQuestion) {
                TrueFalseQuestion tfQuestion = (TrueFalseQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    tfQuestion.editQuestion(newPrompt);
                    outputHandler.displayMessage("True/False question prompt modified successfully!");
                }
            }
            // Check if the question is a MultipleChoiceQuestion
            else if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    mcQuestion.editQuestion(newPrompt);
                }
                String modifyChoices = inputHandler.getInput("Do you want to modify the choices? (yes/no): ");
                if (modifyChoices.equalsIgnoreCase("yes")) {
                    ArrayList<String> choices = mcQuestion.getOptions();
                    for (int i = 0; i < choices.size(); i++) {
                        outputHandler.displayMessage("Current choice " + (i + 1) + ": " + choices.get(i));
                        String newChoice = inputHandler.getInput("Enter new value for choice " + (i + 1) + " (or leave blank to keep current): ");
                        if (!newChoice.isEmpty()) {
                            choices.set(i, newChoice);
                        }
                    }
                    mcQuestion.setOptions(choices);
                    outputHandler.displayMessage("Multiple-choice options modified successfully!");
                }
            }
            // Check if the question is a ShortAnswerQuestion
            else if (question instanceof ShortAnswerQuestion) {
                ShortAnswerQuestion saQuestion = (ShortAnswerQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    saQuestion.editQuestion(newPrompt);
                }
                String modifyLimit = inputHandler.getInput("Do you want to modify the character limit? (yes/no): ");
                if (modifyLimit.equalsIgnoreCase("yes")) {
                    int newLimit = Integer.parseInt(inputHandler.getInput("Enter the new character limit: "));
                    saQuestion.setCharacterLimit(newLimit);
                    outputHandler.displayMessage("Short answer question character limit modified successfully!");
                }
            }
            // Check if the question is a ValidDateQuestion
            else if (question instanceof ValidDateQuestion) {
                ValidDateQuestion dateQuestion = (ValidDateQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    dateQuestion.editQuestion(newPrompt);
                }
            }
            // Check if the question is a MatchingQuestion
            else if (question instanceof MatchingQuestion) {
                MatchingQuestion matchQuestion = (MatchingQuestion) question;
                String modifyPrompt = inputHandler.getInput("Do you want to modify the prompt? (yes/no): ");
                if (modifyPrompt.equalsIgnoreCase("yes")) {
                    String newPrompt = inputHandler.getInput("Enter the new prompt: ");
                    matchQuestion.editQuestion(newPrompt);
                }
                String modifyItems = inputHandler.getInput("Do you want to modify the matching items? (yes/no): ");
                if (modifyItems.equalsIgnoreCase("yes")) {
                    ArrayList<String> leftItems = matchQuestion.getLeftItems();
                    ArrayList<String> rightItems = matchQuestion.getRightItems();
                    for (int i = 0; i < leftItems.size(); i++) {
                        outputHandler.displayMessage("Current left item " + (i + 1) + ": " + leftItems.get(i));
                        String newLeftItem = inputHandler.getInput("Enter new value for left item " + (i + 1) + " (or leave blank to keep current): ");
                        if (!newLeftItem.isEmpty()) {
                            leftItems.set(i, newLeftItem);
                        }
                        outputHandler.displayMessage("Current right item " + (i + 1) + ": " + rightItems.get(i));
                        String newRightItem = inputHandler.getInput("Enter new value for right item " + (i + 1) + " (or leave blank to keep current): ");
                        if (!newRightItem.isEmpty()) {
                            rightItems.set(i, newRightItem);
                        }
                    }
                    matchQuestion.setMatchingItems(leftItems, rightItems);
                    outputHandler.displayMessage("Matching items modified successfully!");
                }
            }
        } else {
            outputHandler.displayMessage("Invalid question number.");
        }
    }



    // Simulates taking the survey
    public Response takeSurvey() {
        if (questions.isEmpty()) {
            outputHandler.displayMessage("No questions available in this survey.");
            return null;
        }

        Response response = new Response(outputHandler);
        outputHandler.displayMessage("Starting Survey: " + surveyName);

        for (Question question : questions) {
            question.displayQuestion();
            String answer = inputHandler.getInput("Your answer: ");
            response.addResponse(answer); // Record each answer in the Response object
        }

        outputHandler.displayMessage("Survey completed.");
        return response;
    }

    // Saves the survey to a file
    public void saveSurvey(String dirPath, String fileName) {
        // Ensure directory exists
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        // Use SerializeHelper to save the Survey object
        System.out.println("about to serialize");
        SerializeHelper.serialize(Survey.class, this, dirPath, fileName);

        outputHandler.displayMessage("Survey saved successfully to " + dirPath + fileName);
    }

    // Loads the survey from a file
    public static Survey loadSurvey(String filePath) {
        Survey loadedSurvey = SerializeHelper.deserialize(Survey.class, filePath);
        return loadedSurvey;
    }

    public String getName() {
        return surveyName;
    }

    public void setOutputHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
    public void setHandlers(OutputHandler outputHandler, InputHandler inputHandler) {
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;

        for (Question question : questions) {
            question.setOutputHandler(outputHandler);
            question.setInputHandler(inputHandler);
        }
    }
}
