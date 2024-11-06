import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidDateQuestion extends Question {
    private LocalDate rangeStart;
    private LocalDate rangeEnd;

    public ValidDateQuestion(String questionText, OutputHandler outputHandler, InputHandler inputHandler) {
        super(questionText, outputHandler, inputHandler);
        this.rangeStart = LocalDate.MIN;  // Set default minimum date
        this.rangeEnd = LocalDate.MAX;    // Set default maximum date
    }

    @Override
    public void displayQuestion() {
        outputHandler.displayMessage(questionText + " (Date format: YYYY-MM-DD)");
    }

    @Override
    public void take() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String response = inputHandler.getInput("Enter a date (YYYY-MM-DD): ");
        LocalDate dateResponse;

        while (true) {
            try {
                dateResponse = LocalDate.parse(response, formatter);
                if (dateResponse.isBefore(rangeStart) || dateResponse.isAfter(rangeEnd)) {
                    response = inputHandler.getInput("Date is out of the allowed range. Please enter a date within " + rangeStart + " and " + rangeEnd + ": ");
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                response = inputHandler.getInput("Invalid date format. Please enter the date in YYYY-MM-DD format: ");
            }
        }
        responses.add(response);
    }

    @Override
    public void editQuestion(String newText) {
        setQuestionText(newText);
    }

    // Optional: Method to set a date range for validation
    public void setDateRange(LocalDate start, LocalDate end) {
        this.rangeStart = start;
        this.rangeEnd = end;
    }
}
