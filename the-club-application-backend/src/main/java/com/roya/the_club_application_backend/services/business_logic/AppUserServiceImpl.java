package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.auth.AppUserDao;
import com.roya.the_club_application_backend.dto.responses.AppUserResponse;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.global.GlobalDao;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDao userDao;
    private final GlobalDao globalDao;

    @Override
    public AppUserResponse getUser(String userId) throws ResourceNotFoundException {
        AppUser user = getUserById(userId);
        return user.toResponse();
    }

    @Override
    public AppUserResponse getUserByPhone(String phone) throws ResourceNotFoundException {
        AppUser user = userDao.findByPhoneNumber(phone)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.APPLICATION, "User not found with phone: " + phone));

        return user.toResponse();
    }

    @Override
    public AppUserResponse getUserByEmail(String email) throws ResourceNotFoundException {
        AppUser user = userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.APPLICATION, "User not found with email: " + email));

        return user.toResponse();
    }

    @Override
    public List<ClubResponse> getClubsOfUser(String userId) {
        String sql = """
                SELECT c.* FROM CLUBS c
                JOIN ROOMS r ON c.club_id = r.room_id
                JOIN ROOM_MEMBERS rm ON rm.room_id = r.room_id
                WHERE rm.user_id = :userId
                """;

        return globalDao.query(sql, globalDao::mapRowToClub, userId);
    }

    @Override
    public List<RoomResponse> getRoomsOfUser(String userId) {
//        String sql = """
//                SELECT r.* FROM ROOMS r
//                JOIN ROOM_MEMBERS rm ON r.room_id = rm.room_id
//                WHERE rm.user_id = :userId
//                """;
//
//        return globalDao.query(sql, globalDao::mapRowToRoom, userId);
        return null;
    }

    @Override
    public List<TaskResponse> getAllTasksOfUser(String userId) {
        String sql = """
                SELECT t.* FROM TASKS t
                JOIN ROOMS r ON t.room_id = r.room_id
                JOIN ROOM_MEMBERS rm ON rm.room_id = r.room_id
                WHERE rm.user_id = :userId
                """;

        return globalDao.query(sql, globalDao::mapRowToTask, userId);
    }

    private AppUser getUserById(String userId) throws ResourceNotFoundException {
        return userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.APPLICATION, "User not found with id: " + userId));
    }

}
