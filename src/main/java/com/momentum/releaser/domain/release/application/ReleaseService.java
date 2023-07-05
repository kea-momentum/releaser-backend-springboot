package com.momentum.releaser.domain.release.application;

import com.momentum.releaser.domain.release.dto.ReleaseRequestDto.ReleaseCreateRequestDto;
import com.momentum.releaser.domain.release.dto.ReleaseResponseDto.ReleaseCreateResponseDto;
import com.momentum.releaser.domain.release.dto.ReleaseResponseDto.ReleasesResponseDto;

public interface ReleaseService {

    /**
     * 5.1 프로젝트별 릴리즈 노트 목록 조회
     */
    ReleasesResponseDto getReleasesByProject(Long projectId);

    /**
     * 5.2 릴리즈 노트 생성
     */
    ReleaseCreateResponseDto createReleaseNote(Long project, ReleaseCreateRequestDto releaseCreateRequestDto);
}
