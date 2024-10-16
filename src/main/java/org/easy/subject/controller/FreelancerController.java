package org.easy.subject.controller;

import org.easy.subject.domain.freelancer.service.FreelancerProfileService;
import org.easy.subject.dto.freelancer.FreelancerProfileDto;
import org.easy.subject.dto.common.PageDto;
import org.easy.subject.dto.freelancer.SearchFreelancerProfileDto;
import org.easy.subject.dto.freelancer.UpdateFreelancerProfileDetailViewDto;
import org.easy.subject.enums.SearchFreelancerSortingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freelancer")
public class FreelancerController {
    private final FreelancerProfileService freelancerProfileService;

    public FreelancerController(
            FreelancerProfileService freelancerProfileService
    ){
        this.freelancerProfileService = freelancerProfileService;
    }

    @GetMapping("/profile/paged")
    public ResponseEntity<PageDto<FreelancerProfileDto>> pagedFreelancerProfiles(
            @RequestParam SearchFreelancerSortingType searchFreelancerSortingType,
            @RequestParam Long startId,
            @RequestParam Long size
    ){
        SearchFreelancerProfileDto searchFreelancerProfileDto = new SearchFreelancerProfileDto(
                searchFreelancerSortingType,
                startId,
                size
        );
        PageDto<FreelancerProfileDto> pageDto = freelancerProfileService.searchFreelancerProfilesBySortingType(searchFreelancerProfileDto);

        return ResponseEntity.ok(pageDto);
    }

    @PutMapping("/profile/detail")
    public ResponseEntity<FreelancerProfileDto> updateFreelancerProfileDetail(
            @RequestBody UpdateFreelancerProfileDetailViewDto updateFreelancerProfileDetailViewDto
    ){
        FreelancerProfileDto freelancerProfileDto =
                freelancerProfileService.increaseViewCountFreelancerProfileById(updateFreelancerProfileDetailViewDto.getId());

        return ResponseEntity.ok(freelancerProfileDto);
    }
}
