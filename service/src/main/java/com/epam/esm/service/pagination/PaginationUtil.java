package com.epam.esm.service.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.IntSupplier;

/**
 * Utility Pagination class
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtil {
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Corrects the index of Page
     *
     * @param pageIndex            Index of Page
     * @param size                 Size of Page
     * @param elementCountSupplier Supplier count of elements
     * @return Index of first Page if pageIndex greater than lastIndex.
     * Index of last Page if pageIndex less than firstIndex, otherwise pageIndex
     */
    public static int correctPageIndex(int pageIndex, int size, IntSupplier elementCountSupplier) {
        int maxPage = elementCountSupplier.getAsInt() / size;
        maxPage = maxPage == ZERO ? ONE : maxPage;
        int correctedPageIndex = pageIndex;
        if (correctedPageIndex - ONE > maxPage) {
            correctedPageIndex = ONE;
        } else if (correctedPageIndex < ONE) {
            correctedPageIndex = maxPage;
        }
        return correctedPageIndex;
    }
}
