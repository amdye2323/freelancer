package org.easy.subject.domain.freelancer.repository;

import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfileEntity, Long>, FreelancerProfileQueryDsl {
}
