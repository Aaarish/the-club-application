package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dto.requests.ClubRequest;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import com.roya.the_club_application_backend.services.business_logic.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

import static com.roya.the_club_application_backend.global.OperationLevel.CLUB;

/*

    Types of operations: access and modification
    Levels of operations: club, room, task, member(part)

    user validation (facade layer):
        ->identify the level and type of operation
        ->check if the user has the right role to perform that operation on that level:
            -if yes: perform the operation (call the service/business-logic layer)
            -if no: throw an exception (and handle that exception to return a proper error response)

 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ClubFacadeService {

//    let me take an example scenario: to create a new club:
//            -validate if the user exists (authentication)
//            -validate if the user is allowed to perform the operation with that operation type and level (authorization : its role-based)
//            -converts the dto object into entity object (dto-entity conversion : its in-built)
//            -call the service method to perform the operation (business logic)

    // so the facade service will act as a door to business-logic intercepting incoming requests
    // controller >> facade >> business-logic >> dao >> database


/*
    validation flow (using role-based authorization):
        -check if the request comes from the logged-in user (logged-in-user = user in auth-context)
        -get the user-role and based on that: either allow the operation or deny it

        -there will be different levels of authorization:
            -first will be application-level: that a user can only access his own data (like his clubs, rooms, tasks)
            -second will be operational-level: that a user with a specific role can perform specific operations (like only club owner can update or delete the club, but members can only access it)
*/


    private final ClubService clubService;
    private final CommonUtils commonUtils;

    public AppResponse createClub(ClubRequest request, AppUser user) throws ResourceNotFoundException {
        ClubResponse club = clubService.createClub(request, user);
        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .message("Club created successfully")
                .response(club)
                .build();
    }

    public AppResponse getClub(String clubId, AppUser user) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.findMemberByClubIdAndUserId(clubId, user.getUserId());

        ClubResponse club = clubService.getClub(clubId);
        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .message("Club fetched successfully")
                .response(club)
                .build();
    }

    public AppResponse updateClub(String clubId, ClubRequest request, AppUser user) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsOwnerOfClub(user.getUserId(), clubId);

        ClubResponse club = clubService.updateClub(clubId, request);
        return AppResponse.builder()
                .source(CLUB)
                .isSuccess(true)
                .message("Club updated successfully")
                .response(club)
                .build();
    }

    public DeleteResponse deleteClub(String clubId, AppUser user) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsOwnerOfClub(user.getUserId(), clubId);

        clubService.deleteClub(clubId);
        return DeleteResponse.builder()
                .isSuccess(true)
                .resourceId(clubId)
                .message("Club deleted successfully")
                .build();
    }

}
