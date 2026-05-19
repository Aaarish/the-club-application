package com.roya.the_club_application_backend.services.business_logic;

public interface InvitationService {
    void inviteUserToClub(String clubId, String senderId, String recipientId);

    void inviteNonUserToClub(String clubId, String senderId);

}
