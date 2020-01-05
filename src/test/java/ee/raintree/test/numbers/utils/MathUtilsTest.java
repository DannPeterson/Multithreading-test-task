package ee.raintree.test.numbers.utils;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MathUtilsTest {
    @Test
    public void givenArmstrongNumbers_whenConfirming_thenTrue() {
        List<BigInteger> armstrongNumbers = new ArrayList<>();
        armstrongNumbers.add(new BigInteger("153"));
        armstrongNumbers.add(new BigInteger("54748"));
        armstrongNumbers.add(new BigInteger("1741725"));
        armstrongNumbers.add(new BigInteger("146511208"));
        armstrongNumbers.add(new BigInteger("28116440335967"));
        armstrongNumbers.add(new BigInteger("1517841543307505039"));
        armstrongNumbers.add(new BigInteger("21887696841122916288858"));
        armstrongNumbers.add(new BigInteger("121204998563613372405438066"));
        armstrongNumbers.add(new BigInteger("1145037275765491025924292050346"));
        armstrongNumbers.add(new BigInteger("1122763285329372541592822900204593"));
        armstrongNumbers.add(new BigInteger("1219167219625434121569735803609966019"));
        armstrongNumbers.add(new BigInteger("115132219018763992565095597973971522400"));

        for (BigInteger armstrongNumber : armstrongNumbers) {
            assertTrue(MathUtils.isArmstrongNumber(armstrongNumber));
        }
    }

    @Test
    public void givenNotArmstrongNumbers_whenConfirming_thenFalse() {
        List<BigInteger> armstrongNumbers = new ArrayList<>();
        armstrongNumbers.add(new BigInteger("152"));
        armstrongNumbers.add(new BigInteger("54747"));
        armstrongNumbers.add(new BigInteger("1741724"));
        armstrongNumbers.add(new BigInteger("146511209"));
        armstrongNumbers.add(new BigInteger("28116440335968"));
        armstrongNumbers.add(new BigInteger("1517841543307505038"));
        armstrongNumbers.add(new BigInteger("21887696841122916288857"));
        armstrongNumbers.add(new BigInteger("121204998563613372405438065"));
        armstrongNumbers.add(new BigInteger("1145037275765491025924292050345"));
        armstrongNumbers.add(new BigInteger("1122763285329372541592822900204592"));
        armstrongNumbers.add(new BigInteger("1219167219625434121569735803609966018"));
        armstrongNumbers.add(new BigInteger("115132219018763992565095597973971522439"));

        for (BigInteger armstrongNumber : armstrongNumbers) {
            assertFalse(MathUtils.isArmstrongNumber(armstrongNumber));
        }
    }
}