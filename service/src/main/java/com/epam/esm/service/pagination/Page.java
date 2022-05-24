package com.epam.esm.service.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * This class is the Pagination Container for DTO
 *
 * @param <T> DTO
 */
@Data
@AllArgsConstructor
public class Page<T> {
    public static final int FIRST_PAGE = 1;
    public static final int ONE = 1;
    public static final int EMPTY = 0;

    /**
     * Index of Page
     */
    private final int index;
    /**
     * Size of Page
     */
    private final int size;
    /**
     * Total count of Elements
     */
    private final int totalElementsCount;
    /**
     * Content of Page
     */
    private final List<T> content;

    /**
     * @return count of all Pages
     */
    public int getTotalPages() {
        return size == EMPTY ? ONE : (int) Math.ceil((double) totalElementsCount / (double) size);
    }

    /**
     * @return true if there is a next page
     */
    public boolean hasNext() {
        return index + ONE <= getTotalPages();
    }

    /**
     * @return true if there is a prev page
     */
    public boolean hasPrevious() {
        return index - ONE > EMPTY;
    }

    /**
     * @return true if there isn't a next page
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * @return next index of Page or index of first Page if there isn't next Page
     */
    public int getNextPageIndex() {
        return hasNext() ? index + ONE : FIRST_PAGE;
    }

    /**
     * @return previous index of Page or index of last Page if there isn't prev Page
     */
    public int getPreviousPageIndex() {
        return hasPrevious() ? index - ONE : getTotalPages();
    }
}
