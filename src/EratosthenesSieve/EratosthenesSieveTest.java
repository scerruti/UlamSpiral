package EratosthenesSieve;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by stephen on 6/23/16.
 */
public class EratosthenesSieveTest {
    @Test
    public void testGetPrimesTo30() throws Exception {
        List<Long> listOfPrimes = Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L);
        assertEquals(listOfPrimes, EratosthenesSieve.getPrimes(30));
    }

    @Test
    public void testGetPrimesTo2012() throws Exception {
        List<Long> listOfPrimes = EratosthenesSieve.getPrimes(2012);
        assertEquals(305, listOfPrimes.size());
    }

    @Test
    public void testSieve20000() throws Exception {
        EratosthenesSieve.sieve(20000, null);
    }
}