package com.movieIndexer.Utils;

import com.movieIndexer.Model.Filme;
import java.util.Comparator;
import java.util.Map;

public class FilterFilmes {

    public static Comparator<Map.Entry<Integer, Filme>> porTitulo() {
        return Comparator.comparing(entry -> entry.getValue().getTitulo(), String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Map.Entry<Integer, Filme>> porDiretor() {
        return Comparator.comparing(entry -> entry.getValue().getDiretor(), String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Map.Entry<Integer, Filme>> porAnoDeLancamento() {
        return Comparator.comparingInt(entry -> entry.getValue().getAnoLancamento());
    }

    public static Comparator<Map.Entry<Integer, Filme>> porDuracao() {
        return Comparator.comparing(entry -> entry.getValue().getDuracaoEmMinutos());
    }

}
