package com.rcet.backend.repository;

import com.rcet.backend.entity.Notice;
import com.rcet.backend.entity.TargetAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByActiveTrue();

    @Query("""
            SELECT n
            FROM Notice n
            WHERE n.active = true
            AND (
                    n.targetAudience = :allAudience
                    OR n.targetAudience = :targetAudience
                )
            AND (
                    n.branch IS NULL
                    OR n.branch.id = :branchId
                )
            AND (
                    n.semester IS NULL
                    OR n.semester.id = :semesterId
                )
            """)
    List<Notice> findStudentNotices(
            @Param("branchId") Long branchId,
            @Param("semesterId") Long semesterId,
            @Param("allAudience") TargetAudience allAudience,
            @Param("targetAudience") TargetAudience targetAudience
    );

    @Query("""
            SELECT n
            FROM Notice n
            WHERE n.active = true
            AND (
                    n.targetAudience = :allAudience
                    OR n.targetAudience = :targetAudience
                )
            AND (
                    n.branch IS NULL
                    OR n.branch.id = :branchId
                )
            """)
    List<Notice> findTeacherNotices(
            @Param("branchId") Long branchId,
            @Param("allAudience") TargetAudience allAudience,
            @Param("targetAudience") TargetAudience targetAudience
    );

}