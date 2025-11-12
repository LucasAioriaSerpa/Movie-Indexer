package com.movieIndexer.Service;

import com.movieIndexer.Model.Filme;
import com.movieIndexer.Model.HashMap;
import com.movieIndexer.Model.Sorts.SortStrategy;
import com.movieIndexer.Utils.LoggingManager;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortService {

    private static final LoggingManager LOG = new LoggingManager();

    public static List<Map.Entry<Integer, Filme>> exportAndSort(
            HashMap table,
            SortStrategy strategy,
            Comparator<Map.Entry<Integer, Filme>> comparator) {

        List<Map.Entry<Integer, Filme>> entries = table.exportEntries();
        if (strategy == null) {
            LOG.logWarning("303", "Nenhuma estratégia de ordenação informada. Retornando itens sem ordenação.", null);
            return entries;
        }

        if (comparator == null) {
            LOG.logWarning("303", "Nenhum comparador informado. Retornando itens sem ordenação.", null);
            return entries;
        }

        long inicio = System.nanoTime();
        strategy.sortMapEntries(entries, comparator);
        long duracao = System.nanoTime() - inicio;

        double duracaoMs = duracao / 1_000_000.0;
        String strategyName = strategy.getClass().getSimpleName();
        if (strategyName == null || strategyName.isBlank()) {
            strategyName = strategy.getClass().getName();
        }

        String comparatorName = comparator.getClass().getSimpleName();
        if (comparatorName == null || comparatorName.isBlank()) {
            comparatorName = comparator.getClass().getName();
        }

        LOG.logInfo(
                "200",
                String.format(
                        "Ordenação concluída usando %s com comparador %s em %.3f ms (itens: %d).",
                        strategyName,
                        comparatorName,
                        duracaoMs,
                        entries.size()
                )
        );

        return entries;
    }

    public static List<Filme> exportAndSortToList(
            HashMap table,
            SortStrategy strategy,
            Comparator<Map.Entry<Integer, Filme>> comparator) {

        return exportAndSort(table, strategy, comparator)
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
