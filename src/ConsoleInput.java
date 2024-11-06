import java.util.Scanner;

public class ConsoleInput extends InputHandler {
    private static final long serialVersionUID = 1L;
    private Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt); // Display the prompt to the user
        String input = scanner.nextLine().trim();

        // Validate input using InputValidator
        while (!InputValidator.isNonEmptyString(input)) {
            System.out.print("Input cannot be empty. " + prompt);
            input = scanner.nextLine().trim();
        }
        return input;
    }
}
