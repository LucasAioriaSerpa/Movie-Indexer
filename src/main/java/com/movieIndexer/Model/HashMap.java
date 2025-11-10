package com.movieIndexer.Model;

import java.util.LinkedList;

public class HashMap {
    private LinkedList<String>[] keys;
    private int size;
    public HashMap(int size) {
        this.size = size;
        keys = new LinkedList[size];
        for (int i = 0; i < size; i++) { keys[i] = new LinkedList<>(); }
    }

    //? GETTERS
    public LinkedList<String>[] getKeys() { return keys; }
    public int getSize() { return size; }

    //? SETTERS
    public void setKeys(LinkedList<String>[] keys) { this.keys = keys; }
    public void setSize(int size) { this.size = size; }

    //? =====================================================================================

    private int hashCode(String key) { return key.hashCode(); }
    


}
