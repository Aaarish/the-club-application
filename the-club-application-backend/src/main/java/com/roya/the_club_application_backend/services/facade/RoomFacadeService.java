package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dto.requests.RoomRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import com.roya.the_club_application_backend.services.business_logic.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static com.roya.the_club_application_backend.global.OperationLevel.CLUB;
import static com.roya.the_club_application_backend.global.OperationLevel.ROOM;

@Service
@RequiredArgsConstructor
public class RoomFacadeService {
    private final RoomService roomService;
    private final CommonUtils commonUtils;

    public AppResponse createRoom(String clubId, RoomRequest request, AppUser user) throws AccessDeniedException {
        commonUtils.checkIfUserIsManagerOfClub(user.getUserId(), clubId);
        RoomResponse room = roomService.createRoom(clubId, request, user.getUserId());

        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .response(room)
                .message("Room created successfully in club with id: " + clubId)
                .build();
    }

    public AppResponse getRoom(String roomId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsARoomMember(roomId, userId);
        RoomResponse room = roomService.getRoom(roomId);

        return AppResponse.builder()
                .source(ROOM)
                .isSuccess(true)
                .response(room)
                .message("Room with id: " + roomId + " has been retrieved successfully.")
                .build();
    }

    public AppResponse updateRoom(String roomId, RoomRequest request, AppUser user) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsManagerOfClub(roomId, user.getUserId());
        RoomResponse room = roomService.updateRoom(roomId, request);

        return AppResponse.builder()
                .source(ROOM)
                .isSuccess(true)
                .response(room)
                .message("Room with id: " + roomId + " has been updated successfully.")
                .build();
    }

    public DeleteResponse deleteRoom(String roomId, AppUser user) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsOwnerOfClub(roomId, user.getUserId());
        roomService.deleteRoom(roomId);

        return DeleteResponse.builder()
                .resourceId(roomId)
                .isSuccess(true)
                .message("Room with id: " + roomId + " has been deleted successfully.")
                .build();
    }

    public AppResponse getRoomMembers(String roomId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsARoomMember(userId, roomId);
        List<MemberResponse> roomMembers = roomService.getRoomMembers(roomId);

        return AppResponse.builder()
                .source(ROOM)
                .isSuccess(true)
                .response(roomMembers)
                .message("Members of room with id: " + roomId + " have been retrieved successfully.")
                .build();
    }

    public AppResponse getClubMembers(String clubId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        return getRoomMembers(clubId, userId);
    }

}
