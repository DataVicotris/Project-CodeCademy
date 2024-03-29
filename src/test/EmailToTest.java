package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import controllers.validationController;

public class EmailToTest {

    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[1].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true;
     *          }
     * 
     */

    @Test
    public void emailWithoutAtSignReturnsFalse() {
        // arrange
        String email = "mickjholster.com";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void emailWithSpaceBeforeAtSignReturnsFalse() {
        // arrange
        String email = "mickjholster @gmail.com";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void emailWithSpaceAfterDotReturnsFalse() {
        // arrange
        String email = "jensdevlaming@gmail. com";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void emailWithoutCharactersBeforeDotReturnsFalse() {
        // arrange
        String email = "mikevandercaaij@.com";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void emailWithOneCharacterAfterDotReturnsTrue() {
        // arrange
        String email = "kaspervanderenden@gmail.c";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(true, result);

        // Deze test klopt niet omdat in de opdracht staat dit:
        // "E-mailadressen hebben de volgende vorm: <minimaal één letter>@<minimaal
        // één letter>.<minimaal één letter>"
        // Wat betekend dat kaspervanderenden@gmail.c gewoon zou moeten werken, maar
        // hier in de opdracht staat dat het niet mag?
        // Hier zeggen ze dat je meer dan 1 letter na de . moet hebben waardoor
        // kaspervanderenden@gmail.c niet zou mogen.
    }

    @Test
    public void validEmailReturnsTrue() {
        // arrange
        String email = "mickjholster@gmail.com";
        // act
        boolean result = validationController.emailChecker(email);
        // assert
        assertEquals(true, result);
    }

}
