package com.epam.esm.repository.filter.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Condition for update according to specific GiftCertificate parameters
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateUpdateCondition {
    private long id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
}
