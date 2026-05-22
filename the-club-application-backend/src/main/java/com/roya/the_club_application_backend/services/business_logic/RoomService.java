package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dto.requests.RoomRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(String clubId, RoomRequest request, String userId) throws ResourceNotFoundException;

    RoomResponse getRoom(String roomId) throws ResourceNotFoundException;

    RoomResponse updateRoom(String roomId, RoomRequest request) throws ResourceNotFoundException;

    void deleteRoom(String roomId) throws ResourceNotFoundException;

    MemberResponse addRoomMember(String roomId, AppUser user) throws ResourceNotFoundException;

    List<MemberResponse> getRoomMembers(String roomId) throws ResourceNotFoundException;

    List<RoomResponse> getRoomsOfClub(String clubId);
}
