public class InputValidator {

    // Checks if the input string is non-empty
    public static boolean isNonEmptyString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Checks if the date input is valid (simplified to check the format "YYYY-MM-DD")
    public static boolean isValidDate(String dateInput) {
        if (dateInput == null || dateInput.trim().isEmpty()) {
            return false;
        }
        // Regex to match date format "YYYY-MM-DD"
        return dateInput.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    // Checks if the input string's length is within a specified character limit
    public static boolean isWithinCharacterLimit(String input, int limit) {
        return input != null && input.length() <= limit;
    }
}
