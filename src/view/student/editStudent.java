package view.student;

import controllers.studentController;
import controllers.validationController;
import controllers.viewController;
import interfaces.Node;
import javafx.geometry.Insets;
// import controllers.courseController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class editStudent implements Node {
    // converted values needed for exequeting the quering
    public static String genderValue = "";
    public static String dateOfBirthValue = "";
    public static String dayValue = "";
    public static String monthValue = "";

    @Override
    public VBox getNode() {
        //VBox
        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });

        //Gridpane for scene
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);

        //Variables for dayOfBirth
        String oldYear;
        String oldMonth;
        String oldDay;

        // split date into day month year format = YYYY-MM-DD and store them in the
        // strings above
        String tempArray[] = Student.oldStudent.getDateOfBirth().split("-");
        oldYear = tempArray[0];
        oldMonth = tempArray[1];
        if (oldMonth.startsWith("0")) {
            String tempMonthArray[] = oldMonth.split("0");
            oldMonth = tempMonthArray[1];
        }
        oldDay = tempArray[2];
        if (oldDay.startsWith("0")) {
            String tempDayArray[] = oldDay.split("0");
            oldDay = tempDayArray[1];
        }

        // title
        Label headerText = new Label("Editing: " + Student.oldStudent.getName());
        headerText.setId("headerTitle");
        // =======================================================================================================
        // email label
        Label emailString = new Label("Email:");
        emailString.getStyleClass().add("labelText");

        // email textfield
        TextField email = new TextField();
        email.getStyleClass().add("cstmTextfield");
        email.setText(Student.oldStudent.getEmail());

        // email error
        Label emailError = new Label("");
        emailError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // name label
        Label nameString = new Label("Name:");
        nameString.getStyleClass().add("labelText");

        // name textfield
        TextField name = new TextField();
        name.getStyleClass().add("cstmTextfield");
        name.setText(Student.oldStudent.getName());

        // name error
        Label nameError = new Label("");
        nameError.getStyleClass().add("errorMsg");
        // =======================================================================================================

        // GridPane for dayofbirth

        GridPane dayOfBirthGrid = new GridPane();

        // day label
        Label dayString = new Label("Day:");
        dayString.getStyleClass().add("labelText");

        // day textfield
        TextField day = new TextField();
        day.getStyleClass().add("cstmTextfield");
        day.setText(oldDay);

        // day errorMsg
        Label dayError = new Label("");
        dayError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // month label
        Label monthString = new Label("Month:");
        monthString.getStyleClass().add("labelText");

        // month textfield
        TextField month = new TextField();
        month.getStyleClass().add("cstmTextfield");
        month.setText(oldMonth);

        System.out.println(Student.oldStudent);

        // month errorMsg
        Label monthError = new Label("");
        monthError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // year label
        Label yearString = new Label("Year:");
        yearString.getStyleClass().add("labelText");

        // year textfield
        TextField year = new TextField();
        year.getStyleClass().add("cstmTextfield");
        year.setText(oldYear);

        // year errorMsg
        Label yearError = new Label("");
        yearError.getStyleClass().add("errorMsg");

        dayOfBirthGrid.setHgap(10);

        dayOfBirthGrid.add(dayString, 0, 0);
        dayOfBirthGrid.add(day, 0, 1);
        dayOfBirthGrid.add(dayError, 0, 2);
        dayOfBirthGrid.add(monthString, 1, 0);
        dayOfBirthGrid.add(month, 1, 1);
        dayOfBirthGrid.add(monthError, 1, 2);
        dayOfBirthGrid.add(yearString, 2, 0);
        dayOfBirthGrid.add(year, 2, 1);
        dayOfBirthGrid.add(yearError, 2, 2);

        // =======================================================================================================
        // gender label
        Label genderString = new Label("Gender:");
        genderString.getStyleClass().add("labelText");

        ComboBox<String> gender = new ComboBox<>();
        gender.getItems().addAll(Gender.MALE.value, Gender.FEMALE.value,
                Gender.OTHER.value);

        if (Student.oldStudent.getGender().equals("F")) {
            gender.setValue("Female");
        } else if (Student.oldStudent.getGender().equals("M")) {
            gender.setValue("Male");
        } else {
            gender.setValue("Other");
        }

        // gender errorMsg
        Label genderError = new Label("");
        genderError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // // city label
        Label cityString = new Label("City:");
        cityString.getStyleClass().add("labelText");

        // city textfield
        TextField city = new TextField();
        city.getStyleClass().add("cstmTextfield");
        city.setText(Student.oldStudent.getCity());

        // city errorMsg
        Label cityError = new Label("");
        cityError.getStyleClass().add("errorMsg");
        // =======================================================================================================

        // street label
        Label streetString = new Label("Street:");
        streetString.getStyleClass().add("labelText");

        // street textfield
        TextField street = new TextField();
        street.getStyleClass().add("cstmTextfield");
        street.setText(Student.oldStudent.getStreet());

        // street errorMsg
        Label streetError = new Label("");
        streetError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // HouseNumber label
        Label houseNumberString = new Label("House Number:");
        houseNumberString.getStyleClass().add("labelText");

        // HouseNumber textfield
        TextField houseNumber = new TextField();
        houseNumber.getStyleClass().add("cstmTextfield");
        houseNumber.setText(Student.oldStudent.getHouseNumber());

        // HouseNumber errorMsg
        Label houseNumberError = new Label("");
        houseNumberError.getStyleClass().add("errorMsg");

        // =======================================================================================================
        // addressgrid
        GridPane addressGrid = new GridPane();
        addressGrid.setHgap(10);
        addressGrid.add(cityString, 0, 0);
        addressGrid.add(city, 0, 1);
        addressGrid.add(cityError, 0, 2);
        addressGrid.add(streetString, 1, 0);
        addressGrid.add(street, 1, 1);
        addressGrid.add(streetError, 1, 2);
        addressGrid.add(houseNumberString, 2, 0);
        addressGrid.add(houseNumber, 2, 1);
        addressGrid.add(houseNumberError, 2, 2);
        // =======================================================================================================
        // postalCode label
        Label postalCodeString = new Label("Postal Code:");
        postalCodeString.getStyleClass().add("labelText");

        // postalCode textfield
        TextField postalCode = new TextField();
        postalCode.getStyleClass().add("cstmTextfield");
        postalCode.setText(Student.oldStudent.getPostalCode());

        // postalCode errorMsg
        Label postalCodeError = new Label("");
        postalCodeError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // country label
        Label countryString = new Label("Country:");
        countryString.getStyleClass().add("labelText");

        // country textfield
        TextField country = new TextField();
        country.getStyleClass().add("cstmTextfield");
        country.setText(Student.oldStudent.getCountry());

        // country errorMsg
        Label countryError = new Label("");
        countryError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // submit button
        Button submitButton = new Button("EDIT STUDENT");
        submitButton.getStyleClass().add("studentSubmitBtn");

        // submit status
        Label submitStatus = new Label("");
        submitStatus.getStyleClass().add("submitStatus");

        // Add fields to pane
        layout.add(headerText, 0, 0);

        layout.add(emailString, 0, 3);
        layout.add(email, 0, 4);
        layout.add(emailError, 0, 5);

        layout.add(nameString, 0, 6);
        layout.add(name, 0, 7);
        layout.add(nameError, 0, 8);

        layout.add(dayOfBirthGrid, 0, 9);

        layout.add(genderString, 0, 10);
        layout.add(gender, 0, 11);
        layout.add(genderError, 0, 12);

        layout.add(addressGrid, 0, 13);

        layout.add(postalCodeString, 0, 14);
        layout.add(postalCode, 0, 15);
        layout.add(postalCodeError, 0, 16);

        layout.add(countryString, 0, 17);
        layout.add(country, 0, 18);
        layout.add(countryError, 0, 19);

        layout.add(submitButton, 0, 20);
        layout.add(submitStatus, 0, 21);

        submitButton.setOnAction((event) -> {
            boolean status = false;
            String response = "";

            // Validate input fields

            if (!validationController.charactersAndSpacesChecker(country.getText())) {
                countryError.setText("Country not valid.");
                status = false;
            } else {
                countryError.setText("");
                status = true;
            }

            if (!validationController.postalCodeChecker(postalCode.getText())) {
                postalCodeError.setText("Postal Code not valid.");
                status = false;
            } else {
                postalCodeError.setText("");
                status = true;
            }

            if (!validationController.houseNumberChecker(houseNumber.getText())) {
                houseNumberError.setText("Address not valid.");
                status = false;
            } else {
                houseNumberError.setText("");
                status = true;
            }

            if (!validationController.charactersAndSpacesChecker(street.getText())) {
                streetError.setText("Street not valid.");
                status = false;
            } else {
                streetError.setText("");
                status = true;
            }

            if (!validationController.charactersAndSpacesChecker(city.getText())) {
                cityError.setText("City not valid.");
                status = false;
            } else {
                cityError.setText("");
                status = true;
            }

            if (gender.getValue() == null) {
                genderError.setText("Gender not valid.");
                status = false;

            } else {
                genderError.setText("");

                if (gender.getValue().equals("Male")) {
                    genderValue = "M";
                } else if (gender.getValue().equals("Female")) {
                    genderValue = "F";
                } else {
                    genderValue = "O";
                }

                status = true;
            }

            if (!validationController.yearChecker(year.getText())) {
                yearError.setText("Year not valid.");
                status = false;
            } else {
                yearError.setText("");
                status = true;
            }

            if (!validationController.monthChecker(month.getText())) {
                monthError.setText("Month not valid.");
                status = false;
            } else {
                monthError.setText("");

                if (month.getText().length() == 1) {
                    monthValue = "0" + month.getText();
                } else {
                    monthValue = month.getText();
                }

                status = true;
            }

            if (!validationController.dayChecker(day.getText())) {
                dayError.setText("Day not valid.");
                status = false;
            } else {
                dayError.setText("");

                if (day.getText().length() == 1) {
                    dayValue = "0" + day.getText();
                } else {
                    dayValue = day.getText();
                }
                status = true;
            }

            if (!validationController.charactersAndSpacesChecker(name.getText())) {
                nameError.setText("Name not valid.");
                status = false;
            } else {
                nameError.setText("");
                status = true;
            }

            if (!validationController.emailChecker(email.getText())) {
                emailError.setText("Email not valid.");
                status = false;
            } else {
                emailError.setText("");
                status = true;
            }

            if (!validationController.dateOfBirthChecker(Integer.valueOf(dayValue), Integer.valueOf(monthValue),
                    Integer.valueOf(year.getText()))) {
                submitStatus.setText("Birthdate not valid");
                dayError.setText("Day not valid.");
                monthError.setText("Month not valid.");
                yearError.setText("Year not valid.");
                status = false;
            } else {
                submitStatus.setText("");
            }

            if (status) {

                dateOfBirthValue = year.getText() + "-" + monthValue + "-" + dayValue;

                studentController.Student updatedStudent = new studentController.Student(email.getText(),
                        name.getText(), dateOfBirthValue, genderValue,
                        street.getText(), houseNumber.getText(),
                        city.getText(), postalCode.getText(), country.getText());
                response = studentController
                        .editStudent(Student.oldStudent, updatedStudent);
                submitStatus.setText(response);
                goBackBtn.setText("Go back");

            }
        });


        //Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(goBackBtn, layout);
        VBox.setMargin(goBackBtn, new Insets(15, 15, 0, 15));

        return root;

    }

}