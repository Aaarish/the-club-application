package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dao.RoomDao;
import com.roya.the_club_application_backend.dto.requests.RoomRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Room;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;
    private final CommonUtils commonUtils;

    @Override
    public RoomResponse createRoom(String clubId, RoomRequest request, String userId) throws ResourceNotFoundException {
        Room room = new Room(request.getName(), request.getDescription(), clubId);
        commonUtils.createPrimaryRoomMembers(userId, room.getRoomId(), clubId);

        Room savedRoom = roomDao.save(room);
        return savedRoom.toResponse();
    }

    @Override
    public RoomResponse getRoom(String roomId) throws ResourceNotFoundException {
        Room room = getRoomById(roomId);
        return room.toResponse();
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(String roomId, RoomRequest request) throws ResourceNotFoundException {
        Room room = getRoomById(roomId);

        if (request.getName() != null) room.changeName(request.getName());
        if (request.getDescription() != null) room.changeDesc(request.getDescription());

        return room.toResponse();
    }

    @Transactional
    @Override
    public void deleteRoom(String roomId) throws ResourceNotFoundException {
        commonUtils.deleteMembersOfRoom(roomId);
        roomDao.deleteById(roomId);
    }

    @Override
    public MemberResponse addRoomMember(String roomId, AppUser user) throws ResourceNotFoundException {
//        Room room = getRoomById(roomId);
//        RoomMember roomMember = new RoomMember(user, room);
//        room.getRoomMembers().add(roomMember);
//
//        roomDao.save(room);
//        return roomMember.toResponse();
        return null;
    }

    @Override
    public List<MemberResponse> getRoomMembers(String roomId) throws ResourceNotFoundException {
//        Room room = getRoomById(roomId);
//
//        return room.getRoomMembers().stream()
//                .map(RoomMember::toResponse)
//                .toList();
        return null;
    }

    @Override
    public List<RoomResponse> getRoomsOfClub(String clubId) {
        return commonUtils.findRoomsByClubId(clubId).stream()
                .map(Room::toResponse)
                .toList();
    }

    private Room getRoomById(String roomId) throws ResourceNotFoundException {
        return roomDao.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.ROOM, "Room not found with id: " + roomId));
    }

}
