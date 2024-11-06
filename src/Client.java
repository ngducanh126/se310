import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Client {
    private Survey currentSurvey;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private SerializeHelper serializeHelper;

    public Client() {
        this.inputHandler = new ConsoleInput(); // Using ConsoleInput as the concrete implementation
        this.outputHandler = new ConsoleOutput(); // Using ConsoleOutput as the concrete implementation
        this.serializeHelper = new SerializeHelper();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMainMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    createSurvey();
                    break;
                case 2:
                    displaySurvey();
                    break;
                case 3:
                    loadSurvey();
                    break;
                case 4:
                    saveSurvey();
                    break;
                case 5:
                    takeSurvey();
                    break;
                case 6:
                    modifySurvey();
                    break;
                case 7:
                    outputHandler.displayMessage("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    outputHandler.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        outputHandler.displayMessage("\nMain Menu");
        outputHandler.displayMessage("1) Create a new Survey");
        outputHandler.displayMessage("2) Display the current Survey");
        outputHandler.displayMessage("3) Load an existing Survey");
        outputHandler.displayMessage("4) Save the current Survey");
        outputHandler.displayMessage("5) Take the current Survey");
        outputHandler.displayMessage("6) Modify the current Survey");
        outputHandler.displayMessage("7) Quit");
        outputHandler.displayMessage("Select an option: ");
    }

    private int getUserChoice(Scanner scanner) {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            outputHandler.displayMessage("Invalid input. Please enter a number.");
        }
        return choice;
    }

    public void createSurvey() {
        outputHandler.displayMessage("Enter the name of the new survey: ");
        String surveyName = inputHandler.getInput("Please enter survey name: ");
        currentSurvey = new Survey(surveyName, outputHandler, inputHandler); // Pass inputHandler here
        outputHandler.displayMessage("Survey created successfully!");

        while (true) {
            displayAddQuestionMenu();
            int choice = getUserChoice(new Scanner(System.in));

            switch (choice) {
                case 1:
                    // True/False Question
                    String tfQuestionText = inputHandler.getInput("Enter the prompt for your True/False question: ");
                    currentSurvey.addQuestion(new TrueFalseQuestion(tfQuestionText, outputHandler, inputHandler));
                    break;

                case 2:
                    // Multiple Choice Question
                    String mcQuestionText = inputHandler.getInput("Enter the prompt for your multiple-choice question: ");
                    ArrayList<String> choices = new ArrayList<>();
                    int numChoices = Integer.parseInt(inputHandler.getInput("Enter the number of choices: "));

                    for (int i = 1; i <= numChoices; i++) {
                        choices.add(inputHandler.getInput("Enter choice #" + i + ": "));
                    }

                    currentSurvey.addQuestion(new MultipleChoiceQuestion(mcQuestionText, choices, outputHandler, inputHandler));
                    break;

                case 3:
                    // Short Answer Question
                    String saQuestionText = inputHandler.getInput("Enter the prompt for your Short Answer question: ");
                    int characterLimit = Integer.parseInt(inputHandler.getInput("Enter the character limit for Short Answer: "));
                    currentSurvey.addQuestion(new ShortAnswerQuestion(saQuestionText, characterLimit, outputHandler, inputHandler));
                    break;

                case 4:
                    // Essay Question
                    String essayQuestionText = inputHandler.getInput("Enter the prompt for your Essay question: ");
                    currentSurvey.addQuestion(new EssayQuestion(essayQuestionText, outputHandler, inputHandler));
                    break;

                case 5:
                    // Valid Date Question
                    String dateQuestionText = inputHandler.getInput("Enter the prompt for your Date question: ");
                    currentSurvey.addQuestion(new ValidDateQuestion(dateQuestionText, outputHandler, inputHandler));
                    break;

                case 6:
                    // Matching Question
                    String matchingQuestionText = inputHandler.getInput("Enter the prompt for your Matching question: ");
                    ArrayList<String> leftItems = new ArrayList<>();
                    ArrayList<String> rightItems = new ArrayList<>();
                    int numItems = Integer.parseInt(inputHandler.getInput("Enter the number of items to match: "));

                    for (int i = 1; i <= numItems; i++) {
                        leftItems.add(inputHandler.getInput("Enter left item #" + i + ": "));
                        rightItems.add(inputHandler.getInput("Enter right item #" + i + ": "));
                    }

                    currentSurvey.addQuestion(new MatchingQuestion(matchingQuestionText, leftItems, rightItems, outputHandler, inputHandler));
                    break;

                case 7:
                    outputHandler.displayMessage("Returning to the main menu.");
                    return;

                default:
                    outputHandler.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    private void displayAddQuestionMenu() {
        outputHandler.displayMessage("\nAdd Question Menu");
        outputHandler.displayMessage("1) Add a new T/F question");
        outputHandler.displayMessage("2) Add a new multiple-choice question");
        outputHandler.displayMessage("3) Add a new short answer question");
        outputHandler.displayMessage("4) Add a new essay question");
        outputHandler.displayMessage("5) Add a new date question");
        outputHandler.displayMessage("6) Add a new matching question");
        outputHandler.displayMessage("7) Return to previous menu");
        outputHandler.displayMessage("Select an option: ");
    }

    public void displaySurvey() {
        if (currentSurvey == null) {
            outputHandler.displayMessage("You must have a survey loaded in order to display it.");
            return;
        }
        currentSurvey.displaySurvey();
    }

    public void loadSurvey() {
        outputHandler.displayMessage("Enter the file path to load the survey: ");
        String filePath = inputHandler.getInput("Please enter the file path to load the survey: ");
        currentSurvey = serializeHelper.deserialize(Survey.class, filePath);
        if (currentSurvey != null) {
            outputHandler.displayMessage("Survey loaded successfully!");
        } else {
            outputHandler.displayMessage("Failed to load survey.");
        }
    }

    public void saveSurvey() {
        if (currentSurvey == null) {
            outputHandler.displayMessage("You must have a survey loaded in order to save it.");
            return;
        }
        outputHandler.displayMessage("Enter the file path to save the survey: ");
        String filePath = inputHandler.getInput("Please enter the file path to save the survey: ");
        serializeHelper.serialize(Survey.class, currentSurvey, "", filePath);
        outputHandler.displayMessage("Survey saved successfully!");
    }

    public void takeSurvey() {
        if (currentSurvey == null) {
            outputHandler.displayMessage("You must have a survey loaded in order to take it.");
            return;
        }
        currentSurvey.takeSurvey();
    }

    public void modifySurvey() {
        if (currentSurvey == null) {
            outputHandler.displayMessage("You must have a survey loaded in order to modify it.");
            return;
        }
        currentSurvey.modifySurvey(); // Pass inputHandler
    }
}
