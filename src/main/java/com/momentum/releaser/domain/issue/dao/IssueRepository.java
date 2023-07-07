package com.momentum.releaser.domain.issue.dao;

import com.momentum.releaser.domain.issue.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="issue", path="issue")
public interface IssueRepository extends JpaRepository<Issue, Long>, IssueRepositoryCustom {
}
