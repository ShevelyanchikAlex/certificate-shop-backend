package com.epam.esm.domain;

import com.epam.esm.audit.AuditListener;
import com.epam.esm.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditListener.class)
public class Order extends AbstractEntity {
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"))
    private List<GiftCertificate> giftCertificates;

    public Order(long id, BigDecimal totalPrice, LocalDateTime createDate, User user, List<GiftCertificate> giftCertificates) {
        super(id);
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.user = user;
        this.giftCertificates = giftCertificates;
    }
}
