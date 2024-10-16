package org.easy.subject.domain.freelancer.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "freelancer_profile")
public class FreelancerProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 16)
    private String name;

    @Column(name = "viewCount", length = 11)
    private Long viewCount;

    @Builder.Default
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}
