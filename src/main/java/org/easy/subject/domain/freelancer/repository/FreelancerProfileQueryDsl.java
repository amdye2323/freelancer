package org.easy.subject.domain.freelancer.repository;

import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;

public interface FreelancerProfileQueryDsl {
    PageDto<FreelancerProfileDto> searchFreelancerProfilesBySortingType(SearchFreelancerProfileDto searchFreelancerProfileDto);
}
