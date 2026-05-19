package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dto.responses.PartResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface PartService {
    PartResponse addPartToTask(String taskId, String memberId, Object proof, String note);

    List<PartResponse> getAllPartsForTask(String taskId);

    PartResponse getPartOfMember(String taskId, String memberId) throws ResourceNotFoundException;

}
