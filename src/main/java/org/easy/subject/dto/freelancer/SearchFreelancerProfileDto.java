package org.easy.subject.dto.freelancer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.easy.subject.enums.SearchFreelancerSortingType;

@Data
@AllArgsConstructor
public class SearchFreelancerProfileDto {
    private SearchFreelancerSortingType sortingType;
    private Long startId;
    private Long pageSize;
}
