package com.momentum.releaser.domain.release.dao.release;

import com.momentum.releaser.domain.project.domain.Project;
import com.momentum.releaser.domain.release.domain.ReleaseNote;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.momentum.releaser.domain.release.domain.QReleaseNote.releaseNote;


@Slf4j
@Repository
@RequiredArgsConstructor
public class ReleaseRepositoryImpl implements ReleaseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 변경하려는 버전이 해당 프로젝트 내에 이미 존재하는 버전인지 확인한다.
     */
    @Override
    public boolean existsByProjectAndVersion(Project project, Long releaseId, String version) {
        return queryFactory
                .selectFrom(releaseNote)
                .where(releaseNote.project.eq(project))
                .where(releaseNote.releaseId.ne(releaseId))
                .where(releaseNote.version.eq(version))
                .fetchFirst() != null;
    }

    /**
     * 가장 최신의 버전을 가져온다.
     */
    @Override
    public Optional<ReleaseNote> findLatestVersionByProject(Project project) {
        ReleaseNote foundReleaseNote = queryFactory
                .selectFrom(releaseNote)
                .where(releaseNote.project.eq(project))
                .orderBy(releaseNote.version.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(foundReleaseNote);
    }

    /**
     * 수정하려는 릴리즈의 기존 버전 값을 뺀 나머지를 전달한다.
     */
    @Override
    public List<ReleaseNote> findByProjectAndNotInVersion(Project project, String version) {
        return queryFactory
                .selectFrom(releaseNote)
                .where(releaseNote.project.eq(project))
                .where(releaseNote.version.ne(version))
                .fetch();
    }

    /**
     * 수정하려는 릴리즈 노트의 이전 릴리즈 노트들을 가져온다.
     */
    @Override
    public List<ReleaseNote> findPreviousReleaseNotes(Project project, String version) {
        // 같은 프로젝트 안의 모든 릴리즈 노트 중 전달받은 버전보다 낮은 버전의 릴리즈 노트 목록을 가져온다.
        return queryFactory
                .selectFrom(releaseNote)
                .where(releaseNote.project.eq(project))
                .where(releaseNote.version.lt(version))
                .orderBy(releaseNote.version.desc())
                .fetch();
    }

    /**
     * 삭제하려는 릴리즈 노트의 이후 릴리즈 노트들을 가져온다.
     */
    @Override
    public List<ReleaseNote> findNextReleaseNotes(Project project, String version) {
        return queryFactory
                .selectFrom(releaseNote)
                .where(releaseNote.project.eq(project))
                .where(releaseNote.version.gt(version))
                .orderBy(releaseNote.version.desc())
                .fetch();
    }
}