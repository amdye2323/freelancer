package org.easy.subject.enums;

public enum SearchFreelancerSortingType {
    NAME("이름"),
    VIEW_COUNT("조회수"),
    CREATED_AT("등록순");

    private final String displayName;

    SearchFreelancerSortingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}