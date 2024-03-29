package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import controllers.validationController;

public class PercentageToTest {

    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     * @requires 0 <= percentage <= 100;
     * @ensures \result = true;
     *          }
     * 
     * @subcontract value out of range low {
     * @requires percentage < 0;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract value out of range high {
     * @requires percentage > 100;
     * @signals \result = false;
     *          }
     * 
     */

    @Test
    public void percentageBetween0And100So50ReturnsTrue() {
        // arrange
        int percentage = 50;
        // act
        boolean result = validationController.percentageChecker(percentage);
        // assert
        assertEquals(true, result);
    }

    @Test
    public void percentageBetween0And100So66ReturnsTrue() {
        // arrange
        int percentage = 66;
        // act
        boolean result = validationController.percentageChecker(percentage);
        // assert
        assertEquals(true, result);
    }

    @Test
    public void percentageHigherThan100SoReturnsFalse() {
        // arrange
        int percentage = 101;
        // act
        boolean result = validationController.percentageChecker(percentage);
        // assert
        assertEquals(false, result);
    }

    @Test
    public void percentageSmallerThan0SoReturnsFalse() {
        // arrange
        int percentage = -1;
        // act
        boolean result = validationController.percentageChecker(percentage);
        // assert
        assertEquals(false, result);
    }
}
