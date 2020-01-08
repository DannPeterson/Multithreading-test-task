package ee.raintree.test.numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class AnalysisPrinter {
    private final static String BAR = "|";
    private final static String SPACE = " ";
    private FileAnalyzerMT analyzer;
    private List<Entry<BigInteger, Integer>> listOfEntries;
    private int chartsCount = 10;
    private int longestEntrySize;
    private int barChartStep;

    public AnalysisPrinter(FileAnalyzerMT analyzer) {
        this.analyzer = analyzer;
        this.listOfEntries = new ArrayList<>(analyzer.getFreqMap().entrySet());
        if (listOfEntries.size() < chartsCount) {
            this.chartsCount = listOfEntries.size();
        }
        this.barChartStep = listOfEntries.get(chartsCount - 1).getValue();
        setLongestEntrySize();
    }

    public void print() {
        printNumbers();
        printBarChart();
    }

    private void printBarChart() {
        System.out.println("10 most frequently appeared numbers: ");
        for (int i = 0; i < chartsCount; i++) {
            Entry<BigInteger, Integer> entry = listOfEntries.get(i);
            int barsCount = entry.getValue() / barChartStep;
            System.out.print(entry.getKey() + getAdditionalSpaces(entry.getKey().toString()) + SPACE);
            for (int bars = 0; bars < barsCount; bars++) {
                System.out.print(BAR);
            }
            System.out.println();
        }
    }

    private void printNumbers() {
        System.out.println("Count of high probability prime numbers: " + analyzer.getPrimeNumbersCount());
        System.out.println("Count of Armstrong numbers: " + analyzer.getArmstrongNumbersCount());
    }

    private void setLongestEntrySize() {
        int longestEntryLength = 0;
        for (int i = 0; i < chartsCount; i++) {
            int entryLength = listOfEntries.get(i).getKey().toString().length();
            if (entryLength > longestEntryLength) {
                longestEntryLength = entryLength;
            }
        }
        longestEntrySize = longestEntryLength;
    }

    private String getAdditionalSpaces(String string) {
        StringBuilder sb = new StringBuilder();
        int needSpaces = longestEntrySize - string.length();
        for (int i = 0; i < needSpaces; i++) {
            sb.append(SPACE);
        }
        return sb.toString();
    }
}