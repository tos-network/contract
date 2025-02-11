package java.lang;

import java.math.BigInteger;
import java.util.Random;

/**
 * A self-contained test class for Arrays.java without using JUnit
 * and without throwing AssertionError. Instead, it prints either
 * "OK" for a passing check or prints the message for a failing check.
 */
public class TestAddress {

    public static void main(String[] args) {
        // Run all tests in sequence
        testToString();

        System.out.println("All tests are executed!");
    }

    /**
     * Prints "OK" if condition == true,
     * otherwise prints the given message.
     */
    private static void assertEquals(boolean condition, String message) {
        if (!condition) {
            // Condition failed => print the message
            System.out.println(message);
        } else {
            // Condition passed => print "OK"
            System.out.println("OK");
        }
    }

    /* ---------------------------------------------------------------------- */
    /*                        BEGIN TEST METHODS                               */
    /* ---------------------------------------------------------------------- */

    private static void testToString() {
        assertEquals(
                new address("52b08330e05d731e38c856c1043288f7d9744").equals(new address("0x00052b08330e05d731e38c856c1043288f7d9744")),
                "52b08330e05d731e38c856c1043288f7d9744 is not equal to 0x00052b08330e05d731e38c856c1043288f7d9744");
        assertEquals(
                new address("0x00052b08330e05d731e38c856c1043288f7d9744").equals(new address("0x00052b08330e05d731e38c856c1043288f7d9744")),
                "0x00052b08330e05d731e38c856c1043288f7d9744 is not equal to 0x00052b08330e05d731e38c856c1043288f7d9744");
    }

}