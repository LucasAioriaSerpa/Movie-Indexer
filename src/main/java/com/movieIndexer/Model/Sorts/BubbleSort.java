
package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BubbleSort implements SortStrategy{

    @Override
    public <K, V> void sortMapEntries(
        List<Map.Entry<K, V>> list,
        Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        if(list.size() <= 1) return;

        int n = list.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Map.Entry<K, V> temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }
}