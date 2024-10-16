package org.easy.subject.factory.freelancer;

import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FreelancerProfileFactory {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FreelancerProfileDto of(FreelancerProfileEntity freelancerProfileEntity){
        return new FreelancerProfileDto(
                freelancerProfileEntity.getId(),
                freelancerProfileEntity.getName(),
                freelancerProfileEntity.getViewCount(),
                freelancerProfileEntity.getCreatedAt().format(formatter)
        );
    }
    public static FreelancerProfileEntity of(FreelancerProfileDto freelancerProfileDto){
        return new FreelancerProfileEntity(
                freelancerProfileDto.getId(),
                freelancerProfileDto.getName(),
                freelancerProfileDto.getViewCount(),
                LocalDateTime.parse(freelancerProfileDto.getCreatedAt(), formatter)
        );
    }
}
