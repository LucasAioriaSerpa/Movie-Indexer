package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InsertionSort implements SortStrategy{

    @Override
    public <K, V> void sortMapEntries(
        List<Map.Entry<K, V>> list,
        Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        insertionSort(list, comparator);
    }

    public <K, V> void insertionSortDummyWay(
        List<Map.Entry<K, V>> list,
        Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        if (list.size() <= 1) { return; }
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Map.Entry<K, V> current = Objects.requireNonNull(
                    list.get(i),
                    "List cannot contain null entries..."
            ); int j = i - 1;
            while (j >= 0) {
                Map.Entry<K, V> previous = list.get(j);
                if (comparator.compare(previous, current) <= 0) { break; }
                list.set(j + 1, previous); j--;
            } list.set(j + 1, current);
        }
    }

    public <K, V> void insertionSort(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        if (list.size() <= 1) { return; }
        sortRecursive(list, list.size() - 1, comparator);
    }

    private <K, V> void sortRecursive(
            List<Map.Entry<K, V>> list,
            int n,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        if (n <= 0) { return; }
        sortRecursive(list, n - 1, comparator);
        Map.Entry<K, V> current = Objects.requireNonNull(
            list.get(n),
            "List cannot contain null entries"
        );
        insertRecursive(list, n, current, comparator);
    }

    private <K, V> void insertRecursive(
            List<Map.Entry<K, V>> list,
            int position,
            Map.Entry<K, V> element,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        if (position == 0 || comparator.compare(list.get(position - 1), element) <= 0) {
            list.set(position, element);
            return;
        }
        list.set(position, list.get(position - 1));
        insertRecursive(list, position - 1, element, comparator);
    }
}
