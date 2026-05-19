package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dto.responses.AppUserResponse;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface AppUserService {
    AppUserResponse getUser(String userId) throws ResourceNotFoundException;

    AppUserResponse getUserByPhone(String phone) throws ResourceNotFoundException;

    AppUserResponse getUserByEmail(String email) throws ResourceNotFoundException;

    List<ClubResponse> getClubsOfUser(String userId);

    List<RoomResponse> getRoomsOfUser(String userId);

    List<TaskResponse> getAllTasksOfUser(String userId);

}
