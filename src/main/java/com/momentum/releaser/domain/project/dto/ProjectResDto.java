package com.momentum.releaser.domain.project.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProjectResDto {

    /**
     * 프로젝트 정보 - 생성, 수정
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProjectInfoRes {
        private Long projectId;
        private String title;
        private String content;
        private String team;
        private String img;
        private Long memberId;
        private String admin;
        private String adminImg;

        @Builder
        public ProjectInfoRes(Long projectId, String title, String content, String team, String img, Long memberId, String admin, String adminImg) {
            this.projectId = projectId;
            this.title = title;
            this.content = content;
            this.team = team;
            this.img = img;
            this.memberId = memberId;
            this.admin = admin;
            this.adminImg = adminImg;
        }
    }

    /**
     * 프로젝트 조회
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProjectRes {
        private List<GetProject> getCreateProjectList;
        private List<GetProject> getEnterProjectList;

        @Builder
        public GetProjectRes(List<GetProject> getCreateProjectList, List<GetProject> getEnterProjectList) {
            this.getCreateProjectList = getCreateProjectList;
            this.getEnterProjectList = getEnterProjectList;
        }
    }

    // 개별 프로젝트 조회
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProject {
        private Long projectId;
        private String title;
        private String content;
        private String team;
        private String img;

        @Builder
        public GetProject(Long projectId, String title, String content, String team, String img) {
            this.projectId = projectId;
            this.title = title;
            this.content = content;
            this.team = team;
            this.img = img;
        }
    }

    /**
     * 프로젝트 멤버 조회
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetMembersRes {
        private Long memberId;
        private Long userId;
        private String name;
        private String img;
        private char position;

        @Builder
        public GetMembersRes(Long memberId, Long userId, String name, String img, char position) {
            this.memberId = memberId;
            this.userId = userId;
            this.name = name;
            this.img = img;
            this.position = position;
        }
    }

}
