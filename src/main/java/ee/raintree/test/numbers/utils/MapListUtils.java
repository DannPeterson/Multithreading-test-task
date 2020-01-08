package ee.raintree.test.numbers.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MapListUtils {
    public static <T> Map<T, Integer> getSortedFreqMapFromList(List<T> list) {
        Map<T, Integer> map = getFreqMapFromList(list);
        Set<Entry<T, Integer>> entries = map.entrySet();
        List<Entry<T, Integer>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, getValueDescComparator());
        Map<T, Integer> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Entry<T, Integer> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }

    public static <T> List<List<T>> splitList(List<T> list, int partitionSize) {
        List<List<T>> partitions = new LinkedList<List<T>>();
        for (int i = 0; i < list.size(); i += partitionSize) {
            partitions.add(list.subList(i,
                    Math.min(i + partitionSize, list.size())));
        }
        return partitions;
    }

    private static <T> Map<T, Integer> getFreqMapFromList(List<T> list) {
        Map<T, Integer> result = new HashMap<>();
        for (T item : list) {
            if (result.get(item) == null) {
                result.put(item, 1);
            } else {
                result.put(item, result.get(item) + 1);
            }
        }
        return result;
    }

    private static <T> Comparator<Entry<T, Integer>> getValueDescComparator() {
        Comparator<Entry<T, Integer>> valueComparator = (e1, e2) -> {
            Integer v1 = e1.getValue();
            Integer v2 = e2.getValue();
            return v2.compareTo(v1);
        };
        return valueComparator;
    }
}