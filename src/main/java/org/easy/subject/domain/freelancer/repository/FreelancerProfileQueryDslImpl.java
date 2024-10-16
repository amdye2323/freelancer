package org.easy.subject.domain.freelancer.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.easy.subject.domain.freelancer.entity.QFreelancerProfileEntity;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.domain.freelancer.entity.FreelancerProfileEntity;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;
import org.easy.subject.enums.SearchFreelancerSortingType;
import org.easy.subject.factory.freelancer.FreelancerProfileFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FreelancerProfileQueryDslImpl implements FreelancerProfileQueryDsl {

    private final JPAQueryFactory queryFactory;
    QFreelancerProfileEntity qFreelancerProfile = QFreelancerProfileEntity.freelancerProfileEntity;

    public FreelancerProfileQueryDslImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public PageDto<FreelancerProfileDto> searchFreelancerProfilesBySortingType(SearchFreelancerProfileDto searchFreelancerProfileDto) {
        SearchFreelancerSortingType searchFreelancerSortingType = searchFreelancerProfileDto.getSortingType();

        JPAQuery<FreelancerProfileEntity> query = queryFactory.selectDistinct(qFreelancerProfile)
                .from(qFreelancerProfile);

        if (searchFreelancerProfileDto.getStartId() != null) {
            query.where(qFreelancerProfile.id.gt(searchFreelancerProfileDto.getStartId()));
        }

        switch (searchFreelancerSortingType) {
            case NAME:
                query.orderBy(qFreelancerProfile.name.asc());
                break;
            case VIEW_COUNT:
                query.orderBy(qFreelancerProfile.viewCount.desc());
                break;
            case CREATED_AT:
                query.orderBy(qFreelancerProfile.createdAt.desc());
                break;
            default:
                throw new IllegalArgumentException("정의되지 않은 정렬 타입입니다. :" + searchFreelancerSortingType);
        }

        List<FreelancerProfileEntity> results = query
                .limit(searchFreelancerProfileDto.getPageSize())
                .fetch();

        long totalCount = queryFactory.selectFrom(qFreelancerProfile).fetchCount();

        List<FreelancerProfileDto> dtoList = results.stream()
                .map(FreelancerProfileFactory::of)
                .collect(Collectors.toList());

        return new PageDto<>(
                searchFreelancerProfileDto.getStartId(),
                searchFreelancerProfileDto.getPageSize(),
                totalCount,
                dtoList
        );
    }
}
