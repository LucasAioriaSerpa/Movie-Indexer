package com.movieIndexer.Utils;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.Sorts.BubbleSort;
import com.movieIndexer.Model.Sorts.InsertionSort;
import com.movieIndexer.Model.Sorts.QuickSort;
import com.movieIndexer.Model.Sorts.SortStrategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SortProvider {

        public static final Map<String, String> STRATEGY_OPTIONS = new LinkedHashMap<>();
        public static final Map<String, String> COMPARATOR_OPTIONS = new LinkedHashMap<>();

        static {
            STRATEGY_OPTIONS.put("Rápido (QuickSort)", "QUICKSORT");
            STRATEGY_OPTIONS.put("Inserção (InsertionSort)", "INSERTIONSORT");
            STRATEGY_OPTIONS.put("Básico (BubbleSort)", "BUBBLESORT");

            COMPARATOR_OPTIONS.put("Por Título", "POR_TITULO");
            COMPARATOR_OPTIONS.put("Por Diretor", "POR_DIRETOR");
            COMPARATOR_OPTIONS.put("Por Ano de Lançamento", "POR_ANO");
            COMPARATOR_OPTIONS.put("Por Duração", "POR_DURACAO");
        }

    public static SortStrategy getStrategy(String strategyName) {
        if (strategyName == null) { return new QuickSort(); }
        switch (strategyName.toUpperCase()) {
            case "QUICKSORT": return new QuickSort();
            case "INSERTIONSORT": return new InsertionSort();
            case "BUBBLESORT": return new BubbleSort();
            default: return new QuickSort();
        }
    }

    public static Comparator<Map.Entry<Integer, Filme>> getComparator(String comparatorName) {
        if (comparatorName == null) { return FilterFilmes.porTitulo(); }
        switch (comparatorName.toUpperCase()) {
            case "POR_TITULO": return FilterFilmes.porTitulo();
            case "POR_DIRETOR": return FilterFilmes.porDiretor();
            case "POR_ANO": return FilterFilmes.porAnoDeLancamento();
            case "POR_DURACAO": return FilterFilmes.porDuracao();
            default: return FilterFilmes.porTitulo();
        }
    }

    public static String getKeyFromValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) { if (entry.getValue().equals(value)) { return entry.getKey(); } }
        return map.keySet().stream().findFirst().orElse(null);
    }

}
