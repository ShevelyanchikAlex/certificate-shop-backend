package com.epam.esm.repository.filter;

public class FilterCondition {
    private String tagName;
    private String name;
    private String description;
    private String sortDirection;
    private boolean sortByDate;
    private boolean sortByName;

    public FilterCondition() {
    }

    public FilterCondition(String tagName, String name, String description, boolean sortByDate,
                           boolean sortByName, String sortDirection) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortByDate = sortByDate;
        this.sortByName = sortByName;
        this.sortDirection = sortDirection;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public boolean isSortByDate() {
        return sortByDate;
    }

    public void setSortByDate(boolean sortByDate) {
        this.sortByDate = sortByDate;
    }

    public boolean isSortByName() {
        return sortByName;
    }

    public void setSortByName(boolean sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
