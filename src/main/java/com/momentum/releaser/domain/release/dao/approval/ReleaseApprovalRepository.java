package com.momentum.releaser.domain.release.dao.approval;

import com.momentum.releaser.domain.project.domain.ProjectMember;
import com.momentum.releaser.domain.release.domain.ReleaseApproval;
import com.momentum.releaser.domain.release.domain.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="release-approval", path="release-approval")
public interface ReleaseApprovalRepository extends JpaRepository<ReleaseApproval, Long>, ReleaseApprovalCustom {

    Optional<ReleaseApproval> findByMemberAndRelease(ProjectMember member, ReleaseNote releaseNote);

    List<ReleaseApproval> findAllByRelease(ReleaseNote releaseNote);
}
