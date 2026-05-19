package com.roya.the_club_application_backend.dto.requests;


import com.roya.the_club_application_backend.entities.Club;
import lombok.Getter;

@Getter
public class ClubRequest {
    private String name;
    private String logo;
    private String description;

    public Club toEntity() {
        return new Club(name, logo, description);
    }

}
