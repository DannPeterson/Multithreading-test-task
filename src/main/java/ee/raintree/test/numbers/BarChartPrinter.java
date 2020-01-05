package ee.raintree.test.numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BarChartPrinter<T> {
    private final static String BAR = "|";
    private final static String SPACE = " ";
    private List<Entry<T, Integer>> listOfEntries;
    private int chartsCount = 10;
    private int longestEntrySize;
    private int barChartStep;

    public BarChartPrinter(Map<T, Integer> map) {
        listOfEntries = new ArrayList<>(map.entrySet());
        if (listOfEntries.size() < chartsCount) {
            chartsCount = listOfEntries.size();
        }
        barChartStep = listOfEntries.get(chartsCount - 1).getValue();
    }

    public void print() {
        setLongestEntrySize();
        printBarChart();
    }

    private void printBarChart() {
        for (int i = 0; i < chartsCount; i++) {
            Entry<T, Integer> entry = listOfEntries.get(i);
            int barsCount = entry.getValue() / barChartStep;
            System.out.print(entry.getKey() + getAdditionalSpaces(entry.getKey().toString()) + SPACE);
            for (int bars = 0; bars < barsCount; bars++) {
                System.out.print(BAR);
            }
            System.out.println();
        }
    }

    private void setLongestEntrySize() {
        int longest = 0;
        for (int i = 0; i < chartsCount; i++) {
            if (listOfEntries.get(i).getKey().toString().length() > longest) {
                longest = listOfEntries.get(i).getKey().toString().length();
            }
        }
        longestEntrySize = longest;
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