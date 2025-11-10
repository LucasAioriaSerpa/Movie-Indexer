package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface SortStrategy {
    <K, V> void sortMapEntries(List<Map.Entry<K, V>> list, Comparator<Map.Entry<K, V>> comparator);
}
