package com.momentum.releaser.domain.project.application;

import com.momentum.releaser.domain.project.dao.ProjectMemberRepository;
import com.momentum.releaser.domain.project.dao.ProjectRepository;
import com.momentum.releaser.domain.project.domain.Project;
import com.momentum.releaser.domain.project.domain.ProjectMember;
import com.momentum.releaser.domain.project.dto.ProjectReqDto.ProjectInfoReq;
import com.momentum.releaser.domain.project.dto.ProjectResDto;
import com.momentum.releaser.domain.project.dto.ProjectResDto.GetProject;
import com.momentum.releaser.domain.project.dto.ProjectResDto.GetProjectRes;
import com.momentum.releaser.domain.project.dto.ProjectResDto.ProjectInfoRes;
import com.momentum.releaser.domain.user.dao.UserRepository;
import com.momentum.releaser.domain.user.domain.User;
import com.momentum.releaser.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.momentum.releaser.global.config.BaseResponseStatus.NOT_EXISTS_PROJECT;
import static com.momentum.releaser.global.config.BaseResponseStatus.NOT_EXISTS_USER;

@Slf4j
@Service
//final 있는 필드만 생성자 만들어줌
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProjectInfoRes createProject(Long userId, ProjectInfoReq registerReq) {
        //user 정보
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));

        //프로젝트 생성
        Project newProject = projectRepository.save(Project.builder()
                .title(registerReq.getTitle())
                .content(registerReq.getContent())
                .team(registerReq.getTeam())
                .img(registerReq.getImg())
                .status('Y')
                .build());

        ProjectMember projectMember = addProjectMember(newProject, user);

        //프로젝트 Response 추가
        ProjectInfoRes registerRes = modelMapper.map(newProject, ProjectInfoRes.class);
        registerRes.setProjectId(newProject.getProjectId());
        registerRes.setAdmin(user.getName());
        registerRes.setMemberId(projectMember.getMemberId());
        registerRes.setAdminImg(user.getImg());
        return registerRes;

    }

    //프로젝트 멤버 추가
    private ProjectMember addProjectMember(Project project, User user) {
        ProjectMember projectMember = ProjectMember.builder()
                .position('L')
                .user(user)
                .project(project)
                .status('Y')
                .build();
        return projectMemberRepository.save(projectMember);
    }

    @Override
    @Transactional
    public ProjectInfoRes updateProject(Long projectId, ProjectInfoReq updateReq) {
        //project 정보
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new CustomException(NOT_EXISTS_PROJECT));

        //project update
        project.updateProject(updateReq);
        Project updateProject = projectRepository.save(project);

        //projectMember 정보
        ProjectMember projectMember = projectMemberRepository.findByProject(updateProject);

        //user 정보
        User user = userRepository.findById(projectMember.getUser().getUserId()).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));

        //프로젝트 Response 추가
        ProjectInfoRes registerRes = modelMapper.map(updateProject, ProjectInfoRes.class);
        registerRes.setProjectId(updateProject.getProjectId());
        registerRes.setAdmin(user.getName());
        registerRes.setMemberId(projectMember.getMemberId());
        registerRes.setAdminImg(user.getImg());

        return registerRes;
    }

    @Override
    @Transactional
    public GetProjectRes getProjects(Long userId) {
        //user 정보
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));

        //project 정보
        List<ProjectMember> projectMemberList = projectMemberRepository.findByUser(user);
        List<GetProject> getCreateProjectList = new ArrayList<>();
        List<GetProject> getEnterProjectList = new ArrayList<>();

        for (ProjectMember projectMember : projectMemberList) {
            //생성한 프로젝트 조회
            if (projectMember.getPosition() == 'L') {
                getCreateProjectList.add(modelMapper.map(projectMember.getProject(), GetProject.class));
            }
            //참가한 프로젝트 조회
            else {
                getEnterProjectList.add(modelMapper.map(projectMember.getProject(), GetProject.class));
            }

        }

        GetProjectRes getProjectRes = GetProjectRes.builder()
                .getCreateProjectList(getCreateProjectList)
                .getEnterProjectList(getEnterProjectList)
                .build();

        return getProjectRes;

    }
}
