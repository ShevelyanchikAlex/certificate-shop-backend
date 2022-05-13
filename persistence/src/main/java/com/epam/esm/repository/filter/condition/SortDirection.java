package com.epam.esm.repository.filter.condition;

/**
 * Direction by sort
 */
public enum SortDirection {
    ASC("ASC"), DESC("DESC");

    private final String sortDirection;

    SortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}