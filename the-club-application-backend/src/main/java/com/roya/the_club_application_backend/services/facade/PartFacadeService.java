package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.dto.responses.PartResponse;
import com.roya.the_club_application_backend.entities.Member;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import com.roya.the_club_application_backend.services.business_logic.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static com.roya.the_club_application_backend.global.OperationLevel.TASK;

@Service
@RequiredArgsConstructor
public class PartFacadeService {
    private final PartService partService;
    private final CommonUtils commonUtils;

    public AppResponse addPartToTask(String taskId, String roomId, Object proof, String note, String userId) throws ResourceNotFoundException {
        Member member = commonUtils.findMemberByRoomIdAndUserId(roomId, userId);
        PartResponse partResponse = partService.addPartToTask(taskId, member.getMemberId(), proof, note);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(partResponse)
                .message("Member with id: " + member.getMemberId() + " has added their part to the task with id: " + taskId)
                .build();
    }

    public AppResponse getAllPartsForTask(String taskId, String roomId, String userId) throws AccessDeniedException {
        commonUtils.checkIfUserIsARoomMember(userId, roomId);
        List<PartResponse> partsForTask = partService.getAllPartsForTask(taskId);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(partsForTask)
                .message("All parts for task with id: " + taskId + " have been retrieved successfully.")
                .build();
    }

    public AppResponse getPartOfMember(String taskId, String roomId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsARoomMember(userId, roomId);
        Member member = commonUtils.findMemberByRoomIdAndUserId(roomId, userId);

        PartResponse partForMember = partService.getPartOfMember(taskId, member.getMemberId());

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(partForMember)
                .message("Part of member: " + member.getMemberId() + " for task with id: " + taskId + " have been retrieved successfully.")
                .build();
    }

}
