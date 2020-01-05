package ee.raintree.test.numbers;

import ee.raintree.test.numbers.utils.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private final static String SPACE = " ";
    private static int fileSize = 67108864;
    private static String fileName;

    public static void main(String args[]) throws InterruptedException, ExecutionException, IOException {
        ArgsChecker.checkArgs(args);
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = args[0];
            fileSize = Integer.valueOf(args[1]) * 1024 * 1024;
        }
        File result = new File(fileName);
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(coreCount);

        // Part 1: Generate numbers and write them to file
        long fileCreationStart = System.currentTimeMillis();
        List<File> tmpFiles = new ArrayList<>();
        List<Future> futureTmpFiles = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            Future<File> futureTmpFile = service.submit(new TmpNumbersFileCreator(fileSize / coreCount));
            futureTmpFiles.add(futureTmpFile);
        }
        for (int i = 0; i < coreCount; i++) {
            Future<File> futureTmpFile = futureTmpFiles.get(i);
            tmpFiles.add(futureTmpFile.get());
        }

        IOCopier.joinFiles(result, tmpFiles);
        long fileCreationEnd = System.currentTimeMillis();
        System.out.println("Numbers file creation done. That took " + (fileCreationEnd - fileCreationStart) + " milliseconds");

        // Part 2: Read numbers from file and analyze them
        long readAndAnalyzeStart = System.currentTimeMillis();

        List<BigInteger> numbers = new ArrayList<>();
        for (String line : Files.readAllLines(result.toPath())) {
            for (String part : line.split(SPACE)) {
                numbers.add(new BigInteger(part));
            }
        }

        int listSize = numbers.size();
        int chunkListSize = listSize / coreCount + 1;
        List<List<BigInteger>> lists = ListSplitter.ofSize(numbers, chunkListSize);

        int countOfPrimeNumbers = 0;
        int countOfArmstrongNumbers = 0;

        List<Future> futurePrimeCounts = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            final int j = i;
            Future<Integer> futurePrimeCount = service.submit(() -> {
                int primeCount = 0;
                for (BigInteger number : lists.get(j)) {
                    if (MathUtils.isPrime(number)) {
                        primeCount++;
                    }
                }
                return primeCount;
            });
            futurePrimeCounts.add(futurePrimeCount);
        }

        for (int i = 0; i < coreCount; i++) {
            Future<Integer> futurePrimeCount = futurePrimeCounts.get(i);
            countOfPrimeNumbers = countOfPrimeNumbers + futurePrimeCount.get();
        }

        List<Future> futureArmstrongCounts = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            final int j = i;
            Future<Integer> futureArmstrongCount = service.submit(() -> {
                int armstrongCount = 0;
                for (BigInteger number : lists.get(j)) {
                    if (MathUtils.isArmstrongNumber(number)) {
                        armstrongCount++;
                    }
                }
                return armstrongCount;
            });
            futureArmstrongCounts.add(futureArmstrongCount);
        }

        for (int i = 0; i < coreCount; i++) {
            Future<Integer> futureArmstrongCount = futureArmstrongCounts.get(i);
            countOfArmstrongNumbers = countOfArmstrongNumbers + futureArmstrongCount.get();
        }

        service.shutdown();
        Map<BigInteger, Integer> numbersFreqMap = MapUtils.getSortedFreqMapFromList(numbers);
        BarChartPrinter printer = new BarChartPrinter(numbersFreqMap);
        long readAndAnalyzeEnd = System.currentTimeMillis();

        // Part 3: Printing result
        System.out.println("File reading and analysis done. That took " + (readAndAnalyzeEnd - readAndAnalyzeStart) + " milliseconds.");
        System.out.println("High probability Prime numbers count: " + countOfPrimeNumbers);
        System.out.println("Armstrong numbers count: " + countOfArmstrongNumbers);
        System.out.println("10 most frequently appeared numbers in bar chart form:");
        printer.print();
    }
}