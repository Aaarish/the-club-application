package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dto.requests.ClubRequest;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

public interface ClubService {
    ClubResponse createClub(ClubRequest request, AppUser user);

    ClubResponse getClub(String clubId) throws ResourceNotFoundException;

    ClubResponse updateClub(String clubId, ClubRequest request) throws ResourceNotFoundException;

    void deleteClub(String clubId) throws ResourceNotFoundException;

}
