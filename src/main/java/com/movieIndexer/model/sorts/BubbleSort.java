
package com.movieIndexer.model.sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BubbleSort {

    public static <K, V> void sortMapEntriesByValue(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Map.Entry<K, V> temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}