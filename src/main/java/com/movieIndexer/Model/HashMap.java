package com.movieIndexer.Model;

import com.movieIndexer.Utils.LoggingManager;

import java.util.*;

public class HashMap {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final LoggingManager LOG = new LoggingManager();

    private static class Node {
        final int key;
        Filme value;
        Node next;

        Node(int key, Filme value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node[] buckets;
    private int size;
    private int capacity;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            LOG.logWarning("303", "Capacidade inicial invalida (" + initialCapacity + "). Usando valor padrao " + DEFAULT_CAPACITY + ".", null);
            initialCapacity = DEFAULT_CAPACITY;
        }
        this.capacity = initialCapacity;
        this.buckets = new Node[capacity];
        this.size = 0;
        LOG.logInfo("100", "HashMap inicializado com capacidade " + this.capacity + ".");
    }
    
    //? ===================================== UTILIDADES ==========================================
    
    private int indexFor(int key) {
        return Math.floorMod(Integer.hashCode(key), capacity);
    }

    private void ensureCapacity() {
        if (size >= capacity * LOAD_FACTOR) {
            int newCapacity = capacity * 2;
            LOG.logInfo("100", "Capacidade atual (" + capacity + ") atingiu o limite. Redimensionando para " + newCapacity + ".");
            resize(newCapacity);
        }

    }

    private void resize(int newCapacity) {
        Node[] oldBuckets = buckets;
        buckets = new Node[newCapacity];
        int oldCapacity = capacity;
        capacity = newCapacity;
        size = 0;
        LOG.logInfo("100", "Reorganizando " + oldCapacity + " buckets para nova capacidade " + newCapacity + ".");

        for (int i = 0; i < oldCapacity; i++) {
            Node head = oldBuckets[i];
            while (head != null) {
                putInternal(head.key, head.value, false);
                head = head.next;
            }

        }
    }

    //? ================================= PRINCIPAIS OPERACOES =================================

    /** Insere ou atualiza o filme com a chave dada. */
    public Filme put(int key, Filme value) {
        return putInternal(key, value, true);
    }

    private Filme putInternal(int key, Filme value, boolean logOperation) {
        if (value == null) {
            NullPointerException ex = new NullPointerException("Filme não pode ser nulo");
            LOG.logError("404", "Tentativa de inserir um filme nulo para a chave " + key + ".", ex);
            throw ex;
        }
        ensureCapacity();

        int idx = indexFor(key);
        Node head = buckets[idx];

        //? Atualiza se já existe um filme com a mesma chave
        for (Node n = head; n != null; n = n.next) {
            if (n.key == key) {
                Filme old = n.value;
                n.value = value;
                if (logOperation) {
                    LOG.logInfo("200", "Filme atualizado para a chave " + key + ".");
                }
                return old;
            }
        }

        //? Insere na cabeça da lista
        buckets[idx] = new Node(key, value, head);
        size++;
        if (logOperation) {
            LOG.logInfo("200", "Filme adicionado para a chave " + key + ". Tamanho atual: " + size + ".");
        }
        return null;
    }

    /**  Retorna o filme associado a uma chave ou null se não existir */
    public Filme get(int key) {
        return getInternal(key, true);
    }

    private Filme getInternal(int key, boolean logOperation) {
        int idx = indexFor(key);
        for (Node n = buckets[idx]; n != null; n = n.next) {
            if (n.key == key) {
                if (logOperation) {
                    LOG.logInfo("100", "Filme encontrado para a chave " + key + ".");
                }
                return n.value;
            }
        }
        if (logOperation) {
            LOG.logWarning("303", "Nenhum filme encontrado para a chave " + key + ".", null);
        }
        return null;
    }

    /** Remove a entrada pela chave. Retorna o filme removido ou null se não existir. */
    public Filme remove(int key) {
        int idx = indexFor(key);
        Node prev = null;
        Node current = buckets[idx];

        while (current != null) {
            if (current.key == key) {
                if (prev == null) buckets[idx] = current.next;
                else prev.next = current.next;
                size--;
                LOG.logInfo("200", "Filme removido para a chave " + key + ". Tamanho atual: " + size + ".");
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        LOG.logWarning("303", "Tentativa de remover chave inexistente " + key + ".", null);
        return null;
    }
    
    public boolean containsKey(int key) { return getInternal(key, false) != null; }
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
        LOG.logInfo("200", "Todos os filmes foram removidos do mapa. Estrutura resetada.");
    }

    //? ==================================== EXPORTAÇÃO =========================================

    public List<Map.Entry<Integer, Filme>> exportEntries() {
        List<Map.Entry<Integer, Filme>> out = new ArrayList<>(size);
        LOG.logInfo("100", "Exportando " + size + " entradas do mapa.");
        exportBucketRecursive(0, out);
        return out;
    }

    private void exportBucketRecursive(int bucketIndex, List<Map.Entry<Integer, Filme>> out) {
        if (bucketIndex >= capacity) return;

        Node n = buckets[bucketIndex];
        while (n != null) {
            out.add(new AbstractMap.SimpleEntry<>(n.key, n.value));
            n = n.next;
        }
        exportBucketRecursive(bucketIndex + 1, out);
    }
    
}
