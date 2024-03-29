package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import controllers.validationController;

public class PostalCodeToTest {

    /**
     * @desc Formats the input postal code to a uniform output in the form
     *       nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital
     *       letters.
     *       Spaces before and after the input string are trimmed.
     * 
     * @subcontract null PostalCode {
     * @requires PostalCode == null;
     * @signals (NullPointerException) PostalCode == null;
     *          }
     * 
     * @subcontract valid PostalCode {
     * @requires Integer.valueOf(PostalCode.trim().substring(0, 4)) > 999 &&
     *           Integer.valueOf(PostalCode.trim().substring(0, 4)) <= 9999 &&
     *           PostalCode.trim().substring(4).trim().length == 2 &&
     *           'A' <=
     *           PostalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z' &&
     *           'A' <=
     *           PostalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z';
     * @ensures \result = PostalCode.trim().substring(0, 4) + " " +
     *          PostalCode.trim().substring(4).trim().toUpperCase()
     *          }
     * 
     * @subcontract invalid PostalCode {
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException);
     *          }
     * 
     */

    @Test
    public void postalCodeNullPointerException() {
        String postalCode = null;
        try {
            validationController.postalCodeChecker(postalCode);
            fail();
        } catch (NullPointerException ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void correctPostalCode4DigitsSpaceAnd2CapitalLettersWithSpaceReturnsTrue() {
        // arrange
        String postalCode = "1111 AZ";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(true, result);
    }

    @Test
    public void PostalCodeWith5DigitsWithSpaceReturnsFalse() {
        // arrange
        String postalCode = "11111 AZ";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void PostalCodeWith3DigitsReturnsWithSpaceFalse() {
        // arrange
        String postalCode = "111 AZ";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void PostalCodeWith4DigitsAnd2SmallLettersWithSpaceReturnFalse() {
        // arrange
        String postalCode = "1111 az";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void PostalCodeWith4DigitsAnd1SmallLetterAnd1CapitalLetterWithSpaceReturnsFalse() {
        // arrange
        String postalCode = "1111 aZ";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void PostalCodeWith4DigitsAnd1CapitalOrSmallLetterWithSpaceReturnsFalse() {
        // arrange
        String postalCode = "1111 A";
        // act
        boolean result = validationController.postalCodeChecker(postalCode);
        // assert
        assertEquals(false, result);
    }

    @Test 
    public void postalCodeWith5DigitsReturnsFalse() {
        //arrange
        String postalCode = "5555";
        //act
        boolean result = validationController.postalCodeChecker(postalCode);
        //assert
        assertEquals(false, result);
    }

    @Test
    public void postalCodeWith4DigitsAnd2CapitalsWithoutSpaceReturnsFalse() {
         //arrange
         String postalCode = "5555AA";
         //act
         boolean result = validationController.postalCodeChecker(postalCode);
         //assert
         assertEquals(false, result);
    }

    @Test
    public void postalCodeWith4DigitsAnd3CapitalsWithSpaceReturnsFalse() {
         //arrange
         String postalCode = "5555 AAA";
         //act
         boolean result = validationController.postalCodeChecker(postalCode);
         //assert
         assertEquals(false, result);
    }

    @Test
    public void postalCodeWith4DigitsAnd2CapitalsWithTwoSpacesReturnsFalse() {
         //arrange
         String postalCode = "5555  AA";
         //act
         boolean result = validationController.postalCodeChecker(postalCode);
         //assert
         assertEquals(false, result);
    }
}
