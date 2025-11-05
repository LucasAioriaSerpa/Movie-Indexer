package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class InsertionSort {

    public static <K, V> void sortMapEntries(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Map.Entry<K, V> current = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), current) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, current);
        }
    }
}
