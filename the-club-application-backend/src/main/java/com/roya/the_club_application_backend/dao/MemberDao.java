package com.roya.the_club_application_backend.dao;

import com.roya.the_club_application_backend.entities.Member;
import com.roya.the_club_application_backend.entities.Member.MemberDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, String> {

    @Query("SELECT rm FROM Member rm WHERE rm.id = :memberId AND rm.memberDegree = :moderator")
    Optional<Member> findByIdAndRole(String memberId, MemberDegree moderator);

    void deleteByRoomId(String roomId);

    Optional<Member> findByClubIdAndUserIdAndMemberDegree(String clubId, String userId, MemberDegree degree);

    void deleteByClubId(String clubId);

    Optional<Member> findByRoomIdAndUserIdAndMemberDegree(String roomId, String userId, MemberDegree moderator);

    Optional<Member> findByClubIdAndUserId(String clubId, String userId);

    List<Member> findByClubId(String clubId);

    List<Member> findByRoomId(String roomId);

    Optional<Member> findByRoomIdAndUserId(String roomId, String userId);

    void deleteByRoomIdAndUserId(String roomId, String userId);

}
