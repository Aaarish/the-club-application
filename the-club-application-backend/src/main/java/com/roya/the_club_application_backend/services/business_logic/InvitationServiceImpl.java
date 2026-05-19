package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dao.InvitationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationDao invitationDao;

    @Override
    public void inviteUserToClub(String clubId, String senderId, String recipientId) {
        // send a club-invitation notification to the user and save the invitation to the databases
    }

    @Override
    public void inviteNonUserToClub(String clubId, String senderId) {
        // create a url with a unique code (containing meta-data of invitation) and save the invitation to the database
    }

}
