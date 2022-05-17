package com.epam.esm.repository.filter.condition;

import com.epam.esm.domain.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Condition for filter according to specific GiftCertificate parameters
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateFilterCondition {
    private String tagName;
    private String name;
    private String description;
    private SortDirection sortDirection;
    private boolean sortByDate;
    private boolean sortByName;
}
