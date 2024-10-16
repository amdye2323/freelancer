package org.easy.subject.domain.freelancer.service;

import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;

public interface FreelancerProfileService {
    PageDto<FreelancerProfileDto> searchFreelancerProfilesBySortingType(SearchFreelancerProfileDto searchFreelancerProfileDto);
    FreelancerProfileDto increaseViewCountFreelancerProfileById(Long id);
}