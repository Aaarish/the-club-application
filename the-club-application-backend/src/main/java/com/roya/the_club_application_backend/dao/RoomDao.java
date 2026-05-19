package com.roya.the_club_application_backend.dao;

import com.roya.the_club_application_backend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDao extends JpaRepository<Room, String> {
    List<Room> findByClubId(String clubId);

    void deleteByClubId(String clubId);

    void deleteByRoomId(String roomId);

//    @Query("INSERT INTO ROOM VALUES (?1, ?2, ?3, ?4) WHERE ?4 IN (SELECT c.id FROM CLUB c)")
//    Room createRoomInClub(String roomId, String name, String desc, String clubId);

}
