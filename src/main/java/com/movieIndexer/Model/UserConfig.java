package com.movieIndexer.Model;

public class UserConfig {

    private String sortingStrategy;
    private String sortingComparator;
    public boolean themeDark;
    private boolean bulkInsertTemplateEnabled;

    public UserConfig() {
        this.sortingStrategy = "QUICKSORT";
        this.sortingComparator = "POR_TITULO";
        this.bulkInsertTemplateEnabled = false;
    }

    public String getSortingStrategy() { return sortingStrategy; }
    public void setSortingStrategy(String sortingStrategy) { this.sortingStrategy = sortingStrategy; }

    public String getSortingComparator() { return sortingComparator; }
    public void setSortingComparator(String sortingComparator) { this.sortingComparator = sortingComparator; }

    public boolean isBulkInsertTemplateEnabled() { return bulkInsertTemplateEnabled; }
    public void setBulkInsertTemplateEnabled(boolean bulkInsertTemplateEnabled) { this.bulkInsertTemplateEnabled = bulkInsertTemplateEnabled; }
}