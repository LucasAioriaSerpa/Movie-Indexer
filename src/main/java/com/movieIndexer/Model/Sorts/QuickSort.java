package com.movieIndexer.Model.Sorts;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Classe utilitária para ordenação usando o algoritmo Quick Sort.
 * Implementa uma versão otimizada com seleção de pivô melhorada (median of three)
 * e uso de Insertion Sort para subarrays pequenos (híbrido).
 * 
 * <p>Complexidade de tempo: O(n log n) no caso médio, O(n²) no pior caso
 * Complexidade de espaço: O(log n) devido à recursão
 * 
 * @author Movie Indexer
 */
public class QuickSort implements SortStrategy{

    /**
     * Limite de tamanho para usar Insertion Sort em vez de Quick Sort.
     * Para subarrays menores que este valor, usa-se Insertion Sort.
     */
    private static final int INSERTION_SORT_THRESHOLD = 10;

    /**
     * Ordena uma lista de entradas de Map usando Quick Sort otimizado.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista a ser ordenada (modificada in-place)
     * @param comparator o comparador usado para ordenar as entradas
     * @throws NullPointerException se list ou comparator forem null
     * @throws IllegalArgumentException se a lista contiver elementos null
     */
    @Override
    public <K, V> void sortMapEntries(
            List<Map.Entry<K, V>> list,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        Objects.requireNonNull(list, "List cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");
        
        if (list.size() <= 1) {
            return;
        }
        
        // Valida que não há elementos null
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                throw new IllegalArgumentException(
                    "List cannot contain null entries at index: " + i
                );
            }
        }
        
        quickSort(list, 0, list.size() - 1, comparator);
    }

    /**
     * Método recursivo principal do Quick Sort.
     * Usa Insertion Sort para subarrays pequenos para melhorar performance.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista a ser ordenada
     * @param low o índice inicial do subarray
     * @param high o índice final do subarray
     * @param comparator o comparador usado para ordenar
     */
    private static <K, V> void quickSort(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        // Otimização: usa Insertion Sort para subarrays pequenos
        if (high - low < INSERTION_SORT_THRESHOLD) {
            insertionSort(list, low, high, comparator);
            return;
        }
        
        if (low < high) {
            // Otimização: seleciona pivô usando median of three
            int pivotIndex = partitionOptimized(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    /**
     * Particiona o array usando o algoritmo de Lomuto com seleção de pivô otimizada.
     * Usa "median of three" para escolher um melhor pivô.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista a ser particionada
     * @param low o índice inicial
     * @param high o índice final
     * @param comparator o comparador usado para comparar
     * @return o índice do pivô após a partição
     */
    private static <K, V> int partitionOptimized(
            List<Map.Entry<K, V>> list,
            int low,
            int high,
            Comparator<Map.Entry<K, V>> comparator
    ) {
        // Seleciona pivô usando median of three
        int pivotIndex = medianOfThree(list, low, high, comparator);
        swap(list, pivotIndex, high);
        
        Map.Entry<K, V> pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        
        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * Seleciona o índice do pivô usando a estratégia "median of three".
     * Escolhe o elemento mediano entre o primeiro, o meio e o último elemento.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista
     * @param low o índice inicial
     * @param high o índice final
     * @param comparator o comparador usado para comparar
     * @return o índice do elemento mediano
     */
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
        
        // Encontra o mediano entre os três
        if (comparator.compare(first, middle) > 0) {
            if (comparator.compare(middle, last) > 0) {
                return mid;
            } else if (comparator.compare(first, last) > 0) {
                return high;
            } else {
                return low;
            }
        } else {
            if (comparator.compare(first, last) > 0) {
                return low;
            } else if (comparator.compare(middle, last) > 0) {
                return high;
            } else {
                return mid;
            }
        }
    }

    /**
     * Implementação otimizada de Insertion Sort para subarrays específicos.
     * Usado como otimização para subarrays pequenos no Quick Sort.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista a ser ordenada
     * @param low o índice inicial do subarray
     * @param high o índice final do subarray
     * @param comparator o comparador usado para ordenar
     */
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
                if (comparator.compare(previous, current) <= 0) {
                    break;
                }
                list.set(j + 1, previous);
                j--;
            }
            list.set(j + 1, current);
        }
    }

    /**
     * Troca dois elementos na lista.
     * 
     * @param <K> o tipo da chave
     * @param <V> o tipo do valor
     * @param list a lista onde trocar os elementos
     * @param i o índice do primeiro elemento
     * @param j o índice do segundo elemento
     */
    private static <K, V> void swap(List<Map.Entry<K, V>> list, int i, int j) {
        if (i == j) {
            return; // Otimização: evita troca desnecessária
        }
        
        Map.Entry<K, V> temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
