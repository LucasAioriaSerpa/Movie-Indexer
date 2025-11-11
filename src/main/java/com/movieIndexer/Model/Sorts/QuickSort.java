package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuickSort implements SortStrategy{

    private static final int INSERTION_SORT_THRESHOLD = 10;

    @Override
    public <K, V> void sortMapEntries(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        if (list.size() <= 1) { return; }
        for (int i = 0; i < list.size(); i++) { if (list.get(i) == null) { throw new IllegalArgumentException("List cannot contain null entries at index: " + i); } }
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <K, V> void quickSort(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        if (high - low < INSERTION_SORT_THRESHOLD) {
            insertionSort(list, low, high, comparator);
            return;
        }
        if (low < high) {
            int pivotIndex = partitionOptimized(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private static <K, V> int partitionOptimized(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        int pivotIndex = medianOfThree(list, low, high, comparator);
        swap(list, pivotIndex, high);
        Map.Entry<K, V> pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) { if (comparator.compare(list.get(j), pivot) <= 0) { i++; swap(list, i, j); } }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static <K, V> int medianOfThree(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        int mid = low + (high - low) / 2;
        Map.Entry<K, V> first = list.get(low);
        Map.Entry<K, V> middle = list.get(mid);
        Map.Entry<K, V> last = list.get(high);
        if (comparator.compare(first, middle) > 0) {
            if (comparator.compare(middle, last) > 0) { return mid; }
            else if (comparator.compare(first, last) > 0) { return high; }
            else { return low; }
        } else {
            if (comparator.compare(first, last) > 0) { return low; }
            else if (comparator.compare(middle, last) > 0) { return high; }
            else { return mid; }
        }
    }

    private static <K, V> void insertionSort(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        for (int i = low + 1; i <= high; i++) {
            Map.Entry<K, V> current = list.get(i);
            int j = i - 1;
            while (j >= low) {
                Map.Entry<K, V> previous = list.get(j);
                if (comparator.compare(previous, current) <= 0) { break; }
                list.set(j + 1, previous); j--;
            }
            list.set(j + 1, current);
        }
    }

    private static <K, V> void swap(List<Map.Entry<K, V>> list, int i, int j) {
        if (i == j) { return; }
        Map.Entry<K, V> temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
