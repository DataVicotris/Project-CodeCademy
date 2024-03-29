package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import controllers.validationController;

public class GradeToTest {

    /**
     * @desc Validates if the grade is within range of 0-10
     * 
     * @subcontract value within valid range {
     * @requires 0.0 <= grade <= 10.0;
     * @ensures \result = true;
     *          }
     * 
     * @subcontract value out of range low {
     * @requires grade < 0.0;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract value out of range high {
     * @requires grade > 10.0;
     * @signals \result = false;
     *          }
     * 
     * @subcontract value has two decimals or more
     * @requires grade = e.g: 2.231 || 3.452 || 3.44 || 4.24 || 6.75;
     * @signals \result = false;
     * 
     */

    @Test
    public void negativeGradeReturnsFalse() {
        //arrange
        String grade = "-2";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(false, result);
    }

    @Test
    public void gradeBiggerThanTenReturnsFalse() {
        //arrange
        String grade = "11";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(false, result);
    }

    @Test
    public void gradeWithTwoDecimalsReturnsFalse() {
        //arrange
        String grade = "6.22";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(false, result);
    }

    @Test
    public void gradeBetweenZeroAndTenWithNoDecimalsReturnsTrue() {
        //arrange
        String grade = "1";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(true, result);
    }

    @Test
    public void gradeSecondBetweenZeroAndTenWithNoDecimalsReturnsTrue() {
        //arrange
        String grade = "7";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(true, result);
    }

    @Test
    public void gradeBetweenZeroAndTenWithOneDecimalReturnsTrue() {
        //arrange
        String grade = "1.2";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(true, result);
    }

    @Test
    public void gradeSecondBetweenZeroAndTenWithOneDecimalReturnsTrue() {
        //arrange
        String grade = "6.5";
        //act
        boolean result = validationController.gradeChecker(grade);
        //assert
        assertEquals(true, result);
    }
}
