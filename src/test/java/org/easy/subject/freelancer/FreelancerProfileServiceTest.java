package org.easy.subject.freelancer;

import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.easy.subject.domain.freelancer.repository.FreelancerProfileRepository;
import org.easy.subject.domain.freelancer.service.FreelancerProfileServiceImpl;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;
import org.easy.subject.enums.SearchFreelancerSortingType;
import org.easy.subject.factory.freelancer.FreelancerProfileFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FreelancerProfileServiceTest {

    @Mock
    private FreelancerProfileRepository freelancerProfileRepository;

    @InjectMocks
    private FreelancerProfileServiceImpl freelancerProfileService;

    @Test
    @DisplayName("정렬 타입에 따른 프리랜서 프로필 검색")
    public void testSearchFreelancerProfilesBySortingType() {
        FreelancerProfileEntity profileEntity = new FreelancerProfileEntity();
        profileEntity.setId(1L);
        profileEntity.setName("테스터");
        profileEntity.setViewCount(10L);
        profileEntity.setCreatedAt(LocalDateTime.now());

        SearchFreelancerProfileDto searchDto = new SearchFreelancerProfileDto(
                SearchFreelancerSortingType.NAME,
                0L,
                10L
        );

        List<FreelancerProfileDto> profileDtos = List.of(FreelancerProfileFactory.of(profileEntity));
        PageDto<FreelancerProfileDto> pageDto = new PageDto<>(null, 10L, 1L, profileDtos);

        when(freelancerProfileRepository.searchFreelancerProfilesBySortingType(searchDto))
                .thenReturn(pageDto);

        PageDto<FreelancerProfileDto> result = freelancerProfileService.searchFreelancerProfilesBySortingType(searchDto);

        assertFalse(result.getData().isEmpty());
        assertEquals(1, result.getData().size());
        assertEquals("테스터", result.getData().get(0).getName());
    }

    @Test
    @DisplayName("프리랜서 프로필 조회 수 증가")
    public void testIncreaseViewCountFreelancerProfileById() {
        FreelancerProfileEntity profileEntity = new FreelancerProfileEntity();
        profileEntity.setId(1L);
        profileEntity.setName("테스터");
        profileEntity.setViewCount(10L);
        profileEntity.setCreatedAt(LocalDateTime.now());

        // 리포지토리 동작 모킹
        when(freelancerProfileRepository.findById(1L))
                .thenReturn(Optional.of(profileEntity));

        when(freelancerProfileRepository.save(any(FreelancerProfileEntity.class)))
                .thenReturn(profileEntity);

        // 서비스 메서드 호출
        FreelancerProfileDto result = freelancerProfileService.increaseViewCountFreelancerProfileById(1L);

        // 검증
        assertNotNull(result);
        assertEquals(11L, result.getViewCount());
        assertEquals("테스터", result.getName());
    }

    @Test
    @DisplayName("존재하지 않는 프로필 ID로 조회 수 증가 시도")
    public void testIncreaseViewCountFreelancerProfileByIdWithInvalidId() {
        when(freelancerProfileRepository.findById(999L))
                .thenReturn(Optional.empty());

        // 예외 검증
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> freelancerProfileService.increaseViewCountFreelancerProfileById(999L)
        );

        assertEquals("존재하지 않는 프로필 아이디 입니다:999", exception.getMessage());
    }
}