package org.easy.subject.domain.freelancer.service;

import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.easy.subject.domain.freelancer.repository.FreelancerProfileRepository;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;
import org.easy.subject.factory.freelancer.FreelancerProfileFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class FreelancerProfileServiceImpl implements FreelancerProfileService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final RedissonClient redissonClient;

    private static final String LOCK_PREFIX = "freelancerProfileUpdateViewCountLock:";

    FreelancerProfileServiceImpl(
            FreelancerProfileRepository freelancerProfileRepository,
            RedissonClient redissonClient
    ){
        this.freelancerProfileRepository = freelancerProfileRepository;
        this.redissonClient = redissonClient;
    }

    @Override
    @Cacheable(value = "freelancerProfiles", key = "#searchFreelancerProfileDto")
    public PageDto<FreelancerProfileDto> searchFreelancerProfilesBySortingType(SearchFreelancerProfileDto searchFreelancerProfileDto) {
        PageDto<FreelancerProfileDto> aa = freelancerProfileRepository.searchFreelancerProfilesBySortingType(searchFreelancerProfileDto);
        return aa;
    }

    @Override
    @Transactional
    @CacheEvict(value = "freelancerProfiles", allEntries = true)
    public FreelancerProfileDto increaseViewCountFreelancerProfileById(Long id) {
        RLock lock = redissonClient.getLock(LOCK_PREFIX + id);
        try {

            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                try {
                    Optional<FreelancerProfileEntity> optionalFreelancerProfileEntity = freelancerProfileRepository.findById(id);

                    if (optionalFreelancerProfileEntity.isPresent()) {
                        FreelancerProfileEntity freelancerProfileEntity = optionalFreelancerProfileEntity.get();
                        freelancerProfileEntity.setViewCount(freelancerProfileEntity.getViewCount() + 1);

                        return FreelancerProfileFactory.of(freelancerProfileRepository.save(freelancerProfileEntity));
                    }
                    throw new NoSuchElementException("존재하지 않는 프로필 아이디 입니다:" + id);
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("잠금 획득에 실패했습니다.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("락 작업이 중단되었습니다.", e);
        }
    }
}
