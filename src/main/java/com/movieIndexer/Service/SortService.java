package com.movieIndexer.Service;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.Sorts.SortStrategy;
import com.movieIndexer.Model.HashMap;
import java.util.*;
import java.util.stream.Collectors;

public class SortService {
    public static List<Map.Entry<Integer, Filme>> exportAndSort(
            HashMap table, SortStrategy strategy,
            Comparator<Map.Entry<Integer, Filme>> comparator) {

        List<Map.Entry<Integer, Filme>> entries = table.exportEntries();
        strategy.sortMapEntries(entries, comparator);
        return entries;
    }

    public static List<Filme> exportAndSortToList(
            HashMap table, SortStrategy strategy,
            Comparator<Map.Entry<Integer, Filme>> comparator) {

        return exportAndSort(table, strategy, comparator)
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
