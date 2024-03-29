package view.course;

public enum courseLevel {

    //Course levels
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    EXPERT("expert");

    final String value;

    courseLevel(String value) {
        this.value = value;
    }

}