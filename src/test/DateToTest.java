package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import controllers.validationController;

public class DateToTest {
   /**
    * @desc Validates is a given date in the form of day, month and year is valid.
    * 
    * @subcontract 31 days in month {
    * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
    *           month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
    * @ensures \result = true;
    *          }
    * 
    * @subcontract 30 days in month {
    * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
    *           1 <= day <= 30;
    * @ensures \result = true;
    *          }
    * 
    * @subcontract 29 days in month {
    * @requires month == 2 && 1 <= day <= 29 &&
    *           (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    * @ensures \result = true;
    *          }
    * 
    * @subcontract 28 days in month {
    * @requires month == 2 && 1 <= day <= 28 &&
    *           (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
    * @ensures \result = true;
    *          }
    * 
    * @subcontract all other cases {
    * @requires no other accepting precondition;
    * @ensures \result = false;
    *          }
    * 
    */

   // All 31 days tests
   @Test
   public void ThirtyOneDaysInJanuaryReturnsTrue() {
      // arrange
      int day = 31;
      int month = 1;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInMarchReturnsTrue() {
      // arrange
      int day = 31;
      int month = 3;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInMayReturnsTrue() {
      // arrange
      int day = 31;
      int month = 5;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInJulyReturnsTrue() {
      // arrange
      int day = 31;
      int month = 7;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInAugustReturnsTrue() {
      // arrange
      int day = 31;
      int month = 8;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInOctoberReturnsTrue() {
      // arrange
      int day = 31;
      int month = 10;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyOneDaysInDecemberReturnsTrue() {
      // arrange
      int day = 31;
      int month = 12;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   // All 30 days tests

   @Test
   public void ThirtyDaysInAprilReturnsTrue() {
      // arrange
      int day = 30;
      int month = 4;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyDaysInJuneReturnsTrue() {
      // arrange
      int day = 30;
      int month = 6;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyDaysInSeptemberReturnsTrue() {
      // arrange
      int day = 30;
      int month = 9;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   @Test
   public void ThirtyDaysInNovemberReturnsTrue() {
      // arrange
      int day = 30;
      int month = 11;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   // All 29 days tests
   @Test
   public void TwentyNineDaysInFebruaryInLeapYearReturnsTrue() {
      // arrange
      int day = 29;
      int month = 2;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   // All 28 days tests
   @Test
   public void TwentyEightDaysInFebruaryInNonLeapYear() {
      // arrange
      int day = 28;
      int month = 2;
      int year = 2001;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(true, result);
   }

   // Wrong tests
   @Test
   public void ThirtyOneDaysInFebruaryReturnsFalse() {
      // arrange
      int day = 31;
      int month = 2;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void ThirteenthMonthReturnsFalse() {
      // arrange
      int day = 31;
      int month = 13;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void ThirtyTwoDaysInOneMonthReturnsFalse() {
      // arrange
      int day = 32;
      int month = 10;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void TwentyNineDaysInFebruaryInNonLeapYearReturnsFalse() {
      // arrange
      int day = 29;
      int month = 2;
      int year = 2001;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      ;
      // assert
      assertEquals(false, result);
   }

   @Test
   public void ThirtyOneDaysInSeptemberReturnsFalse() {
      // arrange
      int day = 31;
      int month = 9;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void NegativeDayReturnsFalse() {
      // arrange
      int day = -15;
      int month = 9;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void NegativeMonthReturnsFalse() {
      // arrange
      int day = 15;
      int month = -9;
      int year = 2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }

   @Test
   public void NegativeYearReturnsFalse() {
      // arrange
      int day = 15;
      int month = 9;
      int year = -2000;
      // act
      boolean result = validationController.dateOfBirthChecker(day, month, year);
      // assert
      assertEquals(false, result);
   }
}
