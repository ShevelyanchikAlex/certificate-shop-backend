package com.epam.esm.service.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.IntSupplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtil {
    private static final int ZERO = 0;
    private static final int ONE = 1;

    public static int correctPageIndex(int page, int size, IntSupplier elementCountSupplier) {
        int maxPage = elementCountSupplier.getAsInt() / size;
        maxPage = maxPage == ZERO ? ONE : maxPage;
        int correctedPage = page;
        if (correctedPage - ONE > maxPage) {
            correctedPage = ONE;
        } else if (correctedPage < ONE) {
            correctedPage = maxPage;
        }
        return correctedPage;
    }
}
