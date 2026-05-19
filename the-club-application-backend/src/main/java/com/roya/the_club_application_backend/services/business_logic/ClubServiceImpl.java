package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.auth.AppUser;
import com.roya.the_club_application_backend.dao.ClubDao;
import com.roya.the_club_application_backend.dto.requests.ClubRequest;
import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.entities.Club;
import com.roya.the_club_application_backend.entities.Room;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {
    private final ClubDao clubDao;
    private final CommonUtils commonUtils;

    @Transactional
    public ClubResponse createClub(ClubRequest request, AppUser user) {
        Club club = request.toEntity();
        club.setOwnerId(user.getUserId());

        Room defaultClubRoom = commonUtils.createDefaultClubRoom(club);
        club.setDefaultRoomId(defaultClubRoom.getRoomId());
        Club savedClub = clubDao.save(club);

        commonUtils.createFirstClubMember(user.getUserId(), defaultClubRoom.getRoomId(), savedClub.getClubId());

        return savedClub.toResponse();
    }

    public ClubResponse getClub(String clubId) throws ResourceNotFoundException {
        return getClubById(clubId).toResponse();
    }

    @Transactional
    public ClubResponse updateClub(String clubId, ClubRequest request) throws ResourceNotFoundException {
        Club club = getClubById(clubId);

        if (request.getName() != null) club.changeName(request.getName());
        if (request.getDescription() != null) club.changeDesc(request.getDescription());
        if (request.getLogo() != null) club.changeLogo(request.getLogo());

        return club.toResponse();
    }

    @Transactional
    public void deleteClub(String clubId) throws ResourceNotFoundException {
        Club club = getClubById(clubId);
        commonUtils.deleteMembersOfClub(clubId);
        commonUtils.deleteRoomsOfClub(clubId);
        clubDao.delete(club);
    }

    private Club getClubById(String clubId) throws ResourceNotFoundException {
        return clubDao.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.CLUB, "Club not found with id: " + clubId));
    }

}
