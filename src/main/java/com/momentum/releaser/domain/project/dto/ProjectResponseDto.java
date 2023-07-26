package com.momentum.releaser.domain.project.dto;

import java.util.List;

import lombok.*;

import com.momentum.releaser.domain.project.dto.ProjectDataDto.GetProjectDateDTO;

public class ProjectResponseDto {

    /**
     * 프로젝트 정보 - 생성, 수정
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProjectInfoResponseDTO {
        private Long projectId;

        @Builder
        public ProjectInfoResponseDTO(Long projectId) {
            this.projectId = projectId;
        }
    }

    /**
     * 프로젝트 조회
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProjectResponseDTO {
        private List<GetProjectDateDTO> getCreateProjectList;
        private List<GetProjectDateDTO> getEnterProjectList;

        @Builder
        public GetProjectResponseDTO(List<GetProjectDateDTO> getCreateProjectList, List<GetProjectDateDTO> getEnterProjectList) {
            this.getCreateProjectList = getCreateProjectList;
            this.getEnterProjectList = getEnterProjectList;
        }
    }

    



}
