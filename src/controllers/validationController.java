package controllers;

public class validationController {

    //All validation methods

    public static boolean emailChecker(String email) {
        String emailPattern = "^\\s*[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$";

        if (email.matches(emailPattern)) {
            return true;
        }
        return false;

    }

    public static boolean charactersAndSpacesChecker(String string) {

        if (string.matches("^\\s*[a-zA-Z]{1}[a-zA-z ]+")) {
            return true;
        }

        return false;
    }

    public static boolean gradeChecker(String grade) {

        if (grade.matches("^(?:10(?:\\.0)?|[1-9](?:\\.[0-9])?|0?\\.[1-9])$")) {
            return true;
        }

        return false;
    }

    public static boolean dayChecker(String day) {

        if (day.matches("^\\s*(?:[1-9]|[12][0-9]|3[01])$")) {
            return true;
        }

        return false;
    }

    public static boolean monthChecker(String month) {

        if (month.matches("^\\s*(?:[1-9]|[1][012])$")) {
            return true;
        }

        return false;
    }

    public static boolean yearChecker(String year) {

        if (year.matches("^\\s*[1-9]{1}[0-9]{3}$")) {
            return true;
        }

        return false;
    }

    public static boolean postalCodeChecker(String postalCode) {
        String postalCodePattern = "^\\s*[1-9][0-9]{3}[ ]{1}[A-Z]{2}$";

        if (postalCode.isEmpty() || postalCode.equals(null)) {
            return false;
            // throw new NullPointerException();
        }

        if (postalCode.matches(postalCodePattern)) {
            return true;
        }
        return false;
    }

    public static boolean houseNumberChecker(String houseNumber) {
        if (houseNumber.matches("^\\s*[1-9]{1}\\d{0,2}[a-zA-Z]{0,1}")) {
            return true;
        }

        return false;
    }

    public static boolean dateOfBirthChecker(int day, int month, int year) {
        // 31 days in month
        if (year >= 1000) {
            if (day >= 1 && day <= 31) {
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    return true;
                }
            }
        }
        
        // 30 days in month
        if (year >= 1000) {
            if (day >= 1 && day <= 30) {
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    return true;
                }
    
            }
        }
       
        // 29 days in month
        if (year >= 1000) {
            if (month == 2 && day >= 1 && day <= 29) {
                if ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) {
                    return true;
                }
            }
        }
      
        // 28 days in month
        if (year >= 1000) {
            if (month == 2 && day >= 1 && day <= 28) {
                if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
                    return true;
                }
            }
        }
        
        // else false
        return false;
    }

    public static boolean introTextChecker(String introText) {

        if (introText.matches("^\\s*[a-zA-Z., ]+")) {
            return true;
        }

        return false;
    }

    public static boolean subjectChecker(String subject) {

        if (subject.matches("^\\s*[a-zA-Z0-9 ]+")) {
            return true;
        }

        return false;
    }

    public static boolean courseNameChecker(String courseName) {

        if (courseName.matches("^\\s*[a-zA-Z0-9 ]+")) {
            return true;
        }

        return false;
    }

    public static boolean percentageChecker(int percentage) {
        if (percentage > 100 || percentage < 0) {
            return false;
        }
        return true;
    }
}
