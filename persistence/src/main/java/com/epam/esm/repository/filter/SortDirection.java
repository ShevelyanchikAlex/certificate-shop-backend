package com.epam.esm.repository.filter;

public enum SortDirection {
    ASC("ASC"), DESC("DESC");

    private static final String ASC_DIRECTION = "ASC";
    private static final String DESC_DIRECTION = "DESC";

    private final String sortDirection;

    SortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public static SortDirection sortDirection(String sortDirection) {
        return switch (sortDirection) {
            case ASC_DIRECTION -> ASC;
            case DESC_DIRECTION -> DESC;
            default -> null;
        };
    }
}