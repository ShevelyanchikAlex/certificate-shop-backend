package com.epam.esm.dto;

import com.epam.esm.domain.Tag;

import java.time.LocalDateTime;
import java.util.Set;

public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private int price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<Tag> tagSet;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(long id, String name, String description, int price, int duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<Tag> tagSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagSet = tagSet;
    }

    public GiftCertificateDto(long id, String name, String description, int price, Set<Tag> tagSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tagSet = tagSet;
    }

    public GiftCertificateDto(long id, String name, String description, int price, int duration,
                              Set<Tag> tagSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tagSet = tagSet;
    }

    public GiftCertificateDto(String name, String description, int price, int duration,
                              LocalDateTime lastUpdateDate, Set<Tag> tagSet) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = lastUpdateDate;
        this.tagSet = tagSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tagSet=" + tagSet +
                '}';
    }
}
