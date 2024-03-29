package view.student;

public enum Gender {

    //Genders
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    public final String value;

    Gender(String value) {
        this.value = value;
    }

}