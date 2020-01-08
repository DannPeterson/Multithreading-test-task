package ee.raintree.test.numbers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ee.raintree.test.numbers.utils.MapListUtils;
import ee.raintree.test.numbers.utils.MathUtils;

public class FileAnalyzerMT {
    private static final char SEPARATOR = ' ';
    private static final int PRIME_INDEX = 0;
    private static final int ARMSTRONG_INDEX = 1;
    private File file;
    private int armstrongNumbersCount;
    private int primeNumbersCount;
    private List<BigInteger> list;
    private Map<BigInteger, Integer> freqMap;
    private ExecutorService service;
    private int coreCount;

    public FileAnalyzerMT(File file) {
        this.file = file;
        this.list = new ArrayList<>();
        this.coreCount = Runtime.getRuntime().availableProcessors();
        this.service = Executors.newFixedThreadPool(coreCount);
    }

    public void analyze() throws IOException, InterruptedException, ExecutionException {
        createListFromFile();
        countSpecialNumbers();
        setFrequencyMap();
    }

    private void createListFromFile() throws IOException {
        StringBuilder numberSb = new StringBuilder();
        try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
            int currentChar;
            while ((currentChar = fis.read()) != -1) {
                currentChar = (char) currentChar;
                if (currentChar == SEPARATOR) {
                    list.add(new BigInteger(numberSb.toString()));
                    numberSb.setLength(0);
                    continue;
                }
                numberSb.append(currentChar);
            }
            if (numberSb.length() > 0) {
                list.add(new BigInteger(numberSb.toString()));
            }
        }
    }

    private void countSpecialNumbers() throws InterruptedException, ExecutionException {
        int partitionListSize = list.size() / coreCount + 1;
        List<List<BigInteger>> lists = MapListUtils.splitList(list, partitionListSize);
        List<Future> futureNumbersCounters = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            final int j = i;
            Future<List<Integer>> futureCounter = service.submit(() -> {
                return countSpecialNumbersInList(lists.get(j));
            });
            futureNumbersCounters.add(futureCounter);
        }
        processFutureCounters(futureNumbersCounters);
        service.shutdown();
    }

    private void setFrequencyMap() {
        freqMap = MapListUtils.getSortedFreqMapFromList(list);
    }

    private List<Integer> countSpecialNumbersInList(List<BigInteger> list) {
        List<Integer> counter = new ArrayList<>();
        counter.add(0);
        counter.add(0);
        for (BigInteger number : list) {
            if (MathUtils.isArmstrongNumber(number)) {
                counter.set(ARMSTRONG_INDEX, counter.get(ARMSTRONG_INDEX) + 1);
            }
            if (MathUtils.isPrime(number)) {
                counter.set(PRIME_INDEX, counter.get(PRIME_INDEX) + 1);
            }
        }
        return counter;
    }

    private void processFutureCounters(List<Future> futureCounters) throws InterruptedException, ExecutionException {
        for (int i = 0; i < coreCount; i++) {
            Future<List<Integer>> futureCounter = futureCounters.get(i);
            List<Integer> counter = futureCounter.get();
            armstrongNumbersCount = armstrongNumbersCount + counter.get(ARMSTRONG_INDEX);
            primeNumbersCount = primeNumbersCount + counter.get(PRIME_INDEX);
        }
    }

    public int getArmstrongNumbersCount() {
        return armstrongNumbersCount;
    }

    public int getPrimeNumbersCount() {
        return primeNumbersCount;
    }

    public Map<BigInteger, Integer> getFreqMap() {
        return freqMap;
    }
}