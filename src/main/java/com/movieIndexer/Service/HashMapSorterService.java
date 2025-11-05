package com.movieIndexer.Service;

import java.util.*;
import java.util.stream.Collectors;

public class HashMapSorterService {
    public <K, V extends Comparable<? super V>> Map<K, V> getSortedMapByValue(Map<K, V> unsortedMap) {
        List<Map.Entry<K, V>> listOfEntries = new ArrayList<>(unsortedMap.entrySet());
        Comparator<Map.Entry<K, V>> valueComparator = Map.Entry.comparingByValue();
        /** TODO: aplicar o resto dos sorts e fazer a logica de "opção" pelo menu
          *  BubbleSort.sortMapEntriesByValue(listOfEntries, valueComparator);
          *  Escolha do usuario por meio JSON
          *
          **/
        return listOfEntries.stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
