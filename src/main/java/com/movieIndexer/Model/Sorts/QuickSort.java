package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class QuickSort {

    public static <K, V> void sortMapEntries(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        if (list == null || list.size() <= 1) { return; }
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <K, V> void quickSort(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private static <K, V> int partition(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        Map.Entry<K, V> pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static <K, V> void swap(List<Map.Entry<K, V>> list, int i, int j) {
        Map.Entry<K, V> temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
