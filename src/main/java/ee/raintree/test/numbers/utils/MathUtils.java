package ee.raintree.test.numbers.utils;

import java.math.BigInteger;

public class MathUtils {
    public static boolean isArmstrongNumber(BigInteger number) {
        String numberInString = number.toString();
        int digitsCount = number.toString().length();
        int power = digitsCount;
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < digitsCount; i++) {
            int digit = Character.getNumericValue(numberInString.charAt(i));
            BigInteger digitInPower = BigInteger.valueOf(digit).pow(power);
            sum = sum.add(digitInPower);
        }

        return sum.compareTo(number) == 0;
    }

    public static boolean isPrime(BigInteger number) {
        return number.isProbablePrime(128);
    }
}