package utils;


import java.util.Date;

public class InputValidator {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    public static boolean validateDate(String input) {
        if (input.matches(DATE_PATTERN)) {
            return true;
        } else {
            System.out.println("Error: Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            return false;
        }
    }

    public static boolean validateDateRange(Date startDate, Date endDate) {

        if (endDate.before(startDate)) {
            System.out.println("End date must be after start date.");
            return false;
        }
        return true;
    }

    public static boolean validateEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Error: The input cannot be empty. Please provide a valid input.");
            return true;
        }
        return false;
    }

}
