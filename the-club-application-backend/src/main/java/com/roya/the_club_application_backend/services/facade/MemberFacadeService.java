package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Member.MemberDegree;
import com.roya.the_club_application_backend.entities.Room;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import com.roya.the_club_application_backend.services.business_logic.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static com.roya.the_club_application_backend.entities.Member.MemberDegree.CLUB_MANAGER;
import static com.roya.the_club_application_backend.entities.Member.MemberDegree.MODERATOR;
import static com.roya.the_club_application_backend.global.OperationLevel.MEMBER;
import static com.roya.the_club_application_backend.global.OperationLevel.ROOM;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {
    private final MemberService memberService;
    private final CommonUtils commonUtils;

    public AppResponse addClubMember(String clubId, String userIdForMember, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsManagerOfClub(userId, clubId);
        Club club = commonUtils.findClubById(clubId);

        MemberResponse memberResponse = memberService.addClubMember(club, userIdForMember);

        return AppResponse.builder()
                .source(ROOM)
                .isSuccess(true)
                .response(memberResponse)
                .message("User with id: " + userIdForMember + " has been added to club with id: " + clubId + " by the moderator with id: "+ userId +  " successfully.")
                .build();
    }

    public AppResponse addRoomMember(String clubId, String roomId, String userIdForMember, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsAModerator(userId, roomId);

        MemberResponse memberResponse = memberService.addRoomMember(clubId, userIdForMember, roomId);

        return AppResponse.builder()
                .source(ROOM)
                .isSuccess(true)
                .response(memberResponse)
                .message("User with id: " + userIdForMember + " has been added to room with id: " + roomId + " by the moderator with id: "+ userId +  " successfully.")
                .build();
    }

    public AppResponse getRoomMember(String memberId) throws ResourceNotFoundException {
        MemberResponse member = memberService.getMemberByMemberId(memberId);

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .response(member)
                .message("Member: " + memberId + " has been retrieved successfully.")
                .build();
    }

    public AppResponse promoteMemberDegree(String roomId, String memberId, String degree, String promoterUserId) throws AccessDeniedException, ResourceNotFoundException {
        if (degree.equals(MODERATOR.name())){
            promoteMemberToModerator(roomId, memberId, promoterUserId);
        } else if (degree.equals(CLUB_MANAGER.name())) {
            promoteModeratorToClubManager(roomId, memberId, promoterUserId);
        } else {
            throw new IllegalArgumentException("Invalid degree: " + degree);
        }

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Member: " + memberId + " has been promoted to " + degree.toLowerCase().replace("_", " ") + " successfully.")
                .build();
    }

    public AppResponse demoteMemberDegree(String roomId, String memberId, String degree, String promoterUserId) throws AccessDeniedException, ResourceNotFoundException {
        if (degree.equals(CLUB_MANAGER.name())) {
            demoteClubManagerToModerator(roomId, memberId, promoterUserId);
        } else if (degree.equals(MODERATOR.name())) {
            demoteModeratorToMember(roomId, memberId, promoterUserId);
        } else {
            throw new IllegalArgumentException("Invalid degree: " + degree);
        }

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Member with id: " + memberId + " has been demoted to " + degree.toLowerCase().replace("_", " ") + " successfully.")
                .build();
    }

    public AppResponse promoteMemberToModerator(String roomId, String memberId, String promoterUserId) throws ResourceNotFoundException, AccessDeniedException {
        commonUtils.checkIfUserIsAModerator(promoterUserId, roomId);
        memberService.changeMemberDegree(memberId, MODERATOR);

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Member with id: " + memberId + " has been promoted to moderator successfully.")
                .build();
    }

    public AppResponse promoteModeratorToClubManager(String roomId, String memberId, String promoterUserId) throws ResourceNotFoundException, AccessDeniedException {
        commonUtils.checkIfUserIsManagerOfClub(promoterUserId, roomId);
        memberService.changeMemberDegree(memberId, CLUB_MANAGER);

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Moderator with id: " + memberId + " has been promoted to club manager successfully.")
                .build();
    }

    public AppResponse demoteClubManagerToModerator(String roomId, String memberId, String demoterUserId) throws ResourceNotFoundException, AccessDeniedException {
        commonUtils.checkIfUserIsManagerOfClub(demoterUserId, roomId);
        memberService.changeMemberDegree(memberId, MODERATOR);

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Club manager with id: " + memberId + " has been demoted to moderator successfully.")
                .build();
    }

    public AppResponse demoteModeratorToMember(String roomId, String memberId, String demoterUserId) throws ResourceNotFoundException, AccessDeniedException {
        commonUtils.checkIfUserIsAModerator(demoterUserId, roomId);
        memberService.changeMemberDegree(memberId, MemberDegree.MEMBER);

        return AppResponse.builder()
                .source(MEMBER)
                .isSuccess(true)
                .message("Moderator with id: " + memberId + " has been demoted to member successfully.")
                .build();
    }

    public DeleteResponse removeRoomMember(String roomId, String memberId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsAModerator(userId, roomId);
        memberService.removeMember(memberId);

        return DeleteResponse.builder()
                .resourceId(memberId)
                .isSuccess(true)
                .message("Member: " + userId + " has been removed from the room: " + roomId + " successfully.")
                .build();
    }

    public DeleteResponse removeClubMember(String clubId, String userIdForMember, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsManagerOfClub(userId, clubId);
        List<Room> clubRooms = commonUtils.findRoomsByClubId(clubId);

        for (Room room : clubRooms) {
            commonUtils.deleteRoomMember(room.getRoomId(), userIdForMember);
        }

        return DeleteResponse.builder()
                .resourceId(userIdForMember)
                .isSuccess(true)
                .message("Member: " + userId + " has been removed from the club: " + clubId + " successfully.")
                .build();
    }

}
