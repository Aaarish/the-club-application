package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dao.MemberDao;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Member;
import com.roya.the_club_application_backend.entities.Member.MemberDegree;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberDao memberDao;

    @Override
    public MemberResponse addRoomMember(String clubId, String userIdForMember, String roomId) {
        Member member = new Member(userIdForMember, roomId, clubId, MemberDegree.MEMBER);
        Member savedMember = memberDao.save(member);
        return savedMember.toResponse();
    }

    @Override
    public MemberResponse getMemberByMemberId(String memberId) throws ResourceNotFoundException {
        return getMemberById(memberId).toResponse();
    }

    @Override
    public List<MemberResponse> getMembersOfClub(String clubId) throws ResourceNotFoundException {
        return memberDao.findByClubId(clubId).stream()
                .map(Member::toResponse)
                .toList();
    }
    
    // clean the APIs (time: 1 hour) : NOW


    // integrate the APIs to frontend (time: 1 hour)

    @Override
    public List<MemberResponse> getMembersOfRoom(String roomId) throws ResourceNotFoundException {
        return memberDao.findByRoomId(roomId).stream()
                .map(Member::toResponse)
                .toList();
    }

    @Override
    public void removeMember(String memberId) throws ResourceNotFoundException {
        Member member = getMemberById(memberId);
        memberDao.delete(member);
    }

    @Override
    public MemberResponse addClubMember(Club club, String userIdForMember) throws ResourceNotFoundException {
        String defaultRoomId = club.getDefaultRoomId();

        Member member = new Member(userIdForMember, defaultRoomId, club.getClubId(), MemberDegree.MEMBER);
        Member savedMember = memberDao.save(member);
        return savedMember.toResponse();
    }

    @Override
    @Transactional
    public void changeMemberDegree(String memberId, MemberDegree degree) throws ResourceNotFoundException {
        Member member = getMemberById(memberId);
        member.changeMemberDegree(degree);
    }

//    @Override
//    @Transactional
//    public MemberResponse changeMemberDegree(String actingMemberId, String subjectMemberId, MemberDegree newSubjectMemberDegree) throws ActionDeniedException, ResourceNotFoundException {
//        Member actingMember = getMemberById(actingMemberId);
//        MemberDegree actingMemberDegree = actingMember.getMemberDegree();
//
//        Member subjectMember = getMemberById(subjectMemberId);
//        MemberDegree currentSubjectMemberDegree = subjectMember.getMemberDegree();
//
//        if (actingMemberDegree.getMemberDegree() <= currentSubjectMemberDegree.getMemberDegree()) {
//            throw new ActionDeniedException(OperationLevel.MEMBER, "Insufficient permissions. Acting member degree: " + actingMemberDegree + ", subject member degree: " + currentSubjectMemberDegree);
//        }
//
//        if (newSubjectMemberDegree.equals(currentSubjectMemberDegree)) {
//            throw new ActionDeniedException(OperationLevel.MEMBER, "The subjectMember is already a " + newSubjectMemberDegree);
//        }
//
//        if ((newSubjectMemberDegree.getMemberDegree() > currentSubjectMemberDegree.getMemberDegree() + 1) || (newSubjectMemberDegree.getMemberDegree() < currentSubjectMemberDegree.getMemberDegree() - 1)) {
//            throw new ActionDeniedException(OperationLevel.MEMBER, "Invalid subjectMember degree change. Cannot skip degrees. Current degree: " + currentSubjectMemberDegree + ", requested degree: " + newSubjectMemberDegree);
//        }
//
//        subjectMember.changeMemberDegree(newSubjectMemberDegree);
//        return subjectMember.toResponse();
//    }

    private Member getMemberById(String memberId) throws ResourceNotFoundException {
        return memberDao.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.MEMBER, "Room member not found with id: " + memberId));
    }

}
