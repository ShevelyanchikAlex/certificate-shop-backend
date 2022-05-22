package com.epam.esm.service.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    public static final int FIRST_PAGE = 1;
    public static final int ONE = 1;
    public static final int EMPTY = 0;

    private final int index;
    private final int size;
    private final int totalElementsCount;
    private final List<T> content;

    public int getTotalPages() {
        return size == EMPTY ? ONE : (int) Math.ceil((double) totalElementsCount / (double) size);
    }

    public boolean hasNext() {
        return index + ONE <= getTotalPages();
    }

    public boolean hasPrevious() {
        return index - ONE > EMPTY;
    }

    public boolean isLast() {
        return !hasNext();
    }

    public int getNextPageIndex() {
        return hasNext() ? index + ONE : FIRST_PAGE;
    }

    public int getPreviousPageIndex() {
        return hasPrevious() ? index - ONE : getTotalPages();
    }
}
