package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Member.MemberDegree;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MemberService {
    MemberResponse addRoomMember(String clubId, String userIdForMember, String roomId);

    MemberResponse getMemberByMemberId(String memberId) throws ResourceNotFoundException;

    List<MemberResponse> getMembersOfClub(String clubId) throws ResourceNotFoundException;

    List<MemberResponse> getMembersOfRoom(String roomId) throws ResourceNotFoundException;

    void changeMemberDegree(String memberId, MemberDegree degree) throws ResourceNotFoundException;

    void removeMember(String memberId) throws ResourceNotFoundException;

    MemberResponse addClubMember(Club club, String userIdForMember) throws ResourceNotFoundException;

}
