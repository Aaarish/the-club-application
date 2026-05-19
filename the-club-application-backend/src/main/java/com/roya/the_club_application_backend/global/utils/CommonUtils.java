package com.roya.the_club_application_backend.global.utils;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.auth.AppUserDao;
import com.roya.the_club_application_backend.dao.ClubDao;
import com.roya.the_club_application_backend.dao.RoomDao;
import com.roya.the_club_application_backend.dao.MemberDao;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Room;
import com.roya.the_club_application_backend.entities.Member;
import com.roya.the_club_application_backend.global.GlobalDao;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static com.roya.the_club_application_backend.entities.Member.MemberDegree.*;

@Service
@RequiredArgsConstructor
public class CommonUtils {
    private final ClubDao clubDao;
    private final RoomDao roomDao;
    private final MemberDao memberDao;
    private final AppUserDao userDao;
    private final GlobalDao globalDao;

    public Room createDefaultClubRoom(Club club) {
        Room room = new Room(club);
        return roomDao.save(room);
    }

    public Room getDefaultClubRoom(String clubId) {
        String roomIdPattern = clubId + "%";
        String sql = "SELECT r.room_id, r.name, r.description, r.created_at, r.club_id FROM rooms r WHERE r.club_id = ? AND r.room_id LIKE ?";

        return globalDao.queryForObject(sql, globalDao::mapRowToRoom, clubId, roomIdPattern);
    }

    public Member createFirstClubMember(String userId, String roomId, String clubId) {
        Member member = new Member(userId, roomId, clubId, CLUB_OWNER);
        return memberDao.save(member);
    }

    public Member createFirstRoomMember(String userId, String roomId, String clubId) {
        Member member = new Member(userId, roomId, clubId, MODERATOR);
        return memberDao.save(member);
    }

    public void checkIfUserIsOwnerOfClub(String userId, String clubId) throws AccessDeniedException {
        memberDao.findByClubIdAndUserIdAndMemberDegree(clubId, userId, CLUB_OWNER)
                .orElseThrow(() -> new AccessDeniedException("User with id: " + userId + " is not owner of club: " + clubId));
    }

    public void checkIfUserIsManagerOfClub(String userId, String clubId) throws AccessDeniedException {
        memberDao.findByClubIdAndUserIdAndMemberDegree(clubId, userId, CLUB_MANAGER)
                .orElseThrow(() -> new AccessDeniedException("User with id: " + userId + " is not a manager of club: " + clubId));
    }

    public void checkIfUserIsAModerator(String userId, String roomId) throws AccessDeniedException, ResourceNotFoundException {
        memberDao.findByRoomIdAndUserIdAndMemberDegree(roomId, userId, MODERATOR)
                .orElseThrow(() -> new AccessDeniedException("User with id: " + userId + " is not a moderator of room with id: " + roomId));
    }

    public void checkIfUserIsARoomMember(String userId, String roomId) throws AccessDeniedException {
        String memberId = userId + "__" + roomId;
        memberDao.findByIdAndRole(memberId, MEMBER)
                .orElseThrow(() -> new AccessDeniedException("User with id: " + userId + " is not a member of room with id: " + roomId));
    }

    public void checkIfRoomExists(String roomId) throws ResourceNotFoundException {
        roomDao.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.ROOM, "Room not found with id: " + roomId));
    }

    public AppUser getUserById(String userId) throws ResourceNotFoundException {
        return userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.APPLICATION, "No user found with id: " + userId));
    }

    public void deleteRoomsOfClub(String clubId) {
        roomDao.deleteByClubId(clubId);
    }

    public void deleteMembersOfClub(String clubId) {
        memberDao.deleteByClubId(clubId);
    }

    public void deleteMembersOfRoom(String roomId) {
        roomDao.deleteByRoomId(roomId);
    }

    public void findMemberByClubIdAndUserId(String clubId, String userId) throws ResourceNotFoundException {
        memberDao.findByClubIdAndUserId(clubId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.CLUB, "No member found with user id: " + userId + " in club with id: " + clubId));
    }

    public Member findMemberByRoomIdAndUserId(String roomId, String userId) throws ResourceNotFoundException {
        return memberDao.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.CLUB, "No member found with user id: " + userId + " in club with id: " + roomId));
    }

    public Club findClubById(String clubId) throws ResourceNotFoundException {
        return clubDao.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.CLUB, "No club found with id: " + clubId));
    }

    public Room findRoomById(String roomId) throws ResourceNotFoundException {
        return roomDao.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.CLUB, "No room found with id: " + roomId));
    }

    public List<Room> findRoomsByClubId(String clubId) {
        return roomDao.findByClubId(clubId);
    }

    public void deleteRoomMember(String roomId, String userId) {
        memberDao.deleteByRoomIdAndUserId(roomId, userId);
    }

}
