package com.epam.esm.repository.filter.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Condition for filter according to specific GiftCertificate parameters
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateFilterCondition {
    private List<String> tagNames;
    private String name;
    private String description;
    private SortDirection sortDirection;
    private boolean sortByDate;
    private boolean sortByName;
}
