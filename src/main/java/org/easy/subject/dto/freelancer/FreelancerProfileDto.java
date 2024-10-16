package org.easy.subject.dto.freelancer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FreelancerProfileDto {
    private Long id;
    private String name;
    private Long viewCount;
    private String createdAt;
}