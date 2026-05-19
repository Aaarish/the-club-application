package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.dto.responses.AppUserResponse;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.services.business_logic.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.roya.the_club_application_backend.global.OperationLevel.APPLICATION;
import static com.roya.the_club_application_backend.global.OperationLevel.CLUB;

@Service
@RequiredArgsConstructor
public class AppUserFacadeService {
    private final AppUserService userService;


    public AppResponse getUser(String userId) throws ResourceNotFoundException {
        AppUserResponse user = userService.getUser(userId);

        return AppResponse.builder()
                .source(APPLICATION)
                .isSuccess(true)
                .response(user)
                .message("User with id: " + userId + " has been retrieved successfully.")
                .build();
    }

    public AppResponse getUserByPhone(String phone) throws ResourceNotFoundException {
        AppUserResponse user = userService.getUserByPhone(phone);

        return AppResponse.builder()
                .source(APPLICATION)
                .isSuccess(true)
                .response(user)
                .message("User with phone: " + phone + " has been retrieved successfully.")
                .build();
    }

    public AppResponse getUserByEmail(String email) throws ResourceNotFoundException {
        AppUserResponse user = userService.getUserByEmail(email);

        return AppResponse.builder()
                .source(APPLICATION)
                .isSuccess(true)
                .response(user)
                .message("User with email: " + email + " has been retrieved successfully.")
                .build();
    }

    public AppResponse getClubsOfUser(String userId) {
        List<ClubResponse> clubsOfUser = userService.getClubsOfUser(userId);

        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .response(clubsOfUser)
                .message("Clubs of user: " + userId + " has been retrieved successfully.")
                .build();
    }

    public AppResponse getRoomsOfUser(String userId) {
        List<RoomResponse> roomsOfUser = userService.getRoomsOfUser(userId);

        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .response(roomsOfUser)
                .message("Rooms of user: " + userId + " has been retrieved successfully.")
                .build();
    }

    public AppResponse getTasksOfUser(String userId) {
        List<TaskResponse> tasksOfUser = userService.getAllTasksOfUser(userId);

        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .response(tasksOfUser)
                .message("Tasks of user: " + userId + " has been retrieved successfully.")
                .build();
    }

}
