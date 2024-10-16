package org.easy.subject.factory.freelancer;

import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;

public class FreelancerProfileFactory {
    public static FreelancerProfileDto of(FreelancerProfileEntity freelancerProfileEntity){
        return new FreelancerProfileDto(
                freelancerProfileEntity.getId(),
                freelancerProfileEntity.getName(),
                freelancerProfileEntity.getViewCount(),
                freelancerProfileEntity.getCreatedAt()
        );
    }
    public static FreelancerProfileEntity of(FreelancerProfileDto freelancerProfileDto){
        return new FreelancerProfileEntity(
                freelancerProfileDto.getId(),
                freelancerProfileDto.getName(),
                freelancerProfileDto.getViewCount(),
                freelancerProfileDto.getCreatedAt()
        );
    }
}
