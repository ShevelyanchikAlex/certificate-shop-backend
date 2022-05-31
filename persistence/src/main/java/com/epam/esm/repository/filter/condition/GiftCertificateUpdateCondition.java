package com.epam.esm.repository.filter.condition;

import com.epam.esm.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal price;
    private Integer duration;
    private List<Tag> tags;
}
