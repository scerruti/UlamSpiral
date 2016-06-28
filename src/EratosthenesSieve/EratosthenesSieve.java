package EratosthenesSieve;

import UlamSpiral.UlamSpiralController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


/**
 * Created by stephen on 6/23/16.
 */
public class EratosthenesSieve {
    static final List<Long> PRIME_SEEDS = Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L, 101L, 103L, 107L, 109L, 113L, 127L, 131L, 137L, 139L, 149L, 151L, 157L, 163L, 167L, 173L, 179L, 181L, 191L, 193L, 197L, 199L, 211L, 223L, 227L, 229L, 233L, 239L, 241L, 251L, 257L, 263L, 269L, 271L, 277L, 281L, 283L, 293L, 307L, 311L, 313L, 317L, 331L, 337L, 347L, 349L, 353L, 359L, 367L, 373L, 379L, 383L, 389L, 397L, 401L, 409L, 419L, 421L, 431L, 433L, 439L, 443L, 449L, 457L, 461L, 463L, 467L, 479L, 487L, 491L, 499L, 503L, 509L, 521L, 523L, 541L, 547L, 557L, 563L, 569L, 571L, 577L, 587L, 593L, 599L, 601L, 607L, 613L, 617L, 619L, 631L, 641L, 643L, 647L, 653L, 659L, 661L, 673L, 677L, 683L, 691L, 701L, 709L, 719L, 727L, 733L, 739L, 743L, 751L, 757L, 761L, 769L, 773L, 787L, 797L, 809L, 811L, 821L, 823L, 827L, 829L, 839L, 853L, 857L, 859L, 863L, 877L, 881L, 883L, 887L, 907L, 911L, 919L, 929L, 937L, 941L, 947L, 953L, 967L, 971L, 977L, 983L, 991L, 997L, 1009L, 1013L, 1019L, 1021L, 1031L, 1033L, 1039L, 1049L, 1051L, 1061L, 1063L, 1069L, 1087L, 1091L, 1093L, 1097L, 1103L, 1109L, 1117L, 1123L, 1129L, 1151L, 1153L, 1163L, 1171L, 1181L, 1187L, 1193L, 1201L, 1213L, 1217L, 1223L, 1229L, 1231L, 1237L, 1249L, 1259L, 1277L, 1279L, 1283L, 1289L, 1291L, 1297L, 1301L, 1303L, 1307L, 1319L, 1321L, 1327L, 1361L, 1367L, 1373L, 1381L, 1399L, 1409L, 1423L, 1427L, 1429L, 1433L, 1439L, 1447L, 1451L, 1453L, 1459L, 1471L, 1481L, 1483L, 1487L, 1489L, 1493L, 1499L, 1511L, 1523L, 1531L, 1543L, 1549L, 1553L, 1559L, 1567L, 1571L, 1579L, 1583L, 1597L, 1601L, 1607L, 1609L, 1613L, 1619L, 1621L, 1627L, 1637L, 1657L, 1663L, 1667L, 1669L, 1693L, 1697L, 1699L, 1709L, 1721L, 1723L, 1733L, 1741L, 1747L, 1753L, 1759L, 1777L, 1783L, 1787L, 1789L, 1801L, 1811L, 1823L, 1831L, 1847L, 1861L, 1867L, 1871L, 1873L, 1877L, 1879L, 1889L, 1901L, 1907L, 1913L, 1931L, 1933L, 1949L, 1951L, 1973L, 1979L, 1987L, 1993L, 1997L, 1999L, 2003L, 2011L);
    public static final int MAX = 1000000;
    static ArrayList<Long> listOfPrimes = new ArrayList<>(PRIME_SEEDS);
    static HashSet<Long> primes = new HashSet<>(PRIME_SEEDS);

    static long nextUntestedNumber = 2012;

    public static void main(String[] args) {
        long start = System.nanoTime();
        sieve(MAX, null);
        int count = 0;
        for (long j = 0; j < 5; j ++) {
            for (long i = 2; i <= MAX; i++) {
                if (isPrime(i)) {
//                System.out.println(i);
                    count++;
                }
            }
        }
        long end = System.nanoTime();
        System.out.format("%d primes found in %.3f seconds.\n", count, (double) (end - start) / (double) 1000000000);

        start = System.nanoTime();
        sieve(MAX, null);
        count = 0;
        for (long j = 0; j < 5; j ++) {
            for (long i = 2; i <= MAX; i++) {
                if (isPrimeToo(i)) {
//                System.out.println(i);
                    count++;
                }
            }
        }
        end = System.nanoTime();
        System.out.format("%d primes found in %.3f seconds.\n", count, (double) (end - start) / (double) 1000000000);


    }

    public static boolean isPrime(long number) {

        if (number >= nextUntestedNumber) {
            sieve(number, null);
        }

        return primes.contains(number);
    }

    public static boolean isPrimeToo(long number) {

        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 6; i <= (Math.sqrt(number) + 1); i += 6) {
            if ((number % (i-1) == 0) || (number % (i + 1) == 0)) return false;
        }
        return true;
    }

    public static void sieve(long number, UlamSpiralController controller) {
        if (number < nextUntestedNumber) return;

        Set<Long> unchecked = LongStream.rangeClosed(nextUntestedNumber, number).boxed().collect(Collectors.toSet());

        if (controller != null) {
            controller.setProgressMessage("Sieving primes");
            controller.setMaxProgress(unchecked.size());
        }

        // Remove known primes
        for (long aPrime : listOfPrimes) {
            for (long multipleOfCurrentPrime = aPrime * aPrime;
                 multipleOfCurrentPrime <= number && !unchecked.isEmpty();
                 multipleOfCurrentPrime += aPrime) {
                if (unchecked.remove(multipleOfCurrentPrime) && controller != null) {
                    controller.incrementProgress();
                }
            }
            if (unchecked.isEmpty()) break;
        }

        List<Long> uncheckedList = new ArrayList<>(unchecked);
        Collections.sort(uncheckedList);
        while (!uncheckedList.isEmpty()) {
            long currentPrime = uncheckedList.remove(0);
            unchecked.remove(currentPrime);
            primes.add(currentPrime);
            listOfPrimes.add(currentPrime);
            int uncheckedSize = unchecked.size();

            for (long multipleOfCurrentPrime = currentPrime * currentPrime;
                 multipleOfCurrentPrime <= number && !unchecked.isEmpty();
                 multipleOfCurrentPrime += currentPrime) {
                if (unchecked.remove(multipleOfCurrentPrime) && controller != null) {
                    controller.incrementProgress();
                }
            }

            /**
             * If nothing was removed then the remaining numbers are prime
             */
            if (uncheckedSize == unchecked.size() && (currentPrime * currentPrime) > number) {
                primes.addAll(uncheckedList);
                listOfPrimes.addAll(uncheckedList);
                uncheckedList.clear();
                if (controller != null) {
                    controller.finishProgress();
                }
            } else {
                uncheckedList = new ArrayList<>(unchecked);
                Collections.sort(uncheckedList);
            }
        }
        nextUntestedNumber = number + 1;

    }

    public static List<Long> getPrimes(long endValue) {
        List<Long> primeList = new ArrayList<>();
        isPrime(endValue);

        for (Long prime : listOfPrimes) {
            if (prime < endValue) {
                primeList.add(prime);
            } else {
                break;
            }
        }
        return primeList;
    }
}
