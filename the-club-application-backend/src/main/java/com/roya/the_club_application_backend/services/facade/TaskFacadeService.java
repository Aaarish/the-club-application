package com.roya.the_club_application_backend.services.facade;

import com.roya.the_club_application_backend.dto.events.TaskEvent;
import com.roya.the_club_application_backend.dto.requests.TaskRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.global.utils.CommonUtils;
import com.roya.the_club_application_backend.services.business_logic.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static com.roya.the_club_application_backend.global.OperationLevel.TASK;

@Service
@RequiredArgsConstructor
public class TaskFacadeService {
    private final TaskService taskService;
    private final CommonUtils commonUtils;

    public AppResponse createTask(String roomId, TaskRequest request, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsARoomMember(roomId, userId);
        TaskResponse task = taskService.createTask(roomId, request, userId);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(task)
                .message("Task has been created successfully.")
                .build();
    }

    public AppResponse getTask(String roomId, String taskId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsARoomMember(roomId, userId);
        TaskResponse task = taskService.getTask(taskId);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(task)
                .message("Task has been retrieved successfully.")
                .build();
    }

    public AppResponse updateTask(String roomId, String taskId, TaskRequest request, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsAModerator(userId, roomId);
        TaskResponse task = taskService.updateTask(taskId, request);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(task)
                .message("Task has been updated successfully.")
                .build();
    }

    public DeleteResponse deleteTask(String roomId, String taskId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        taskService.deleteTask(taskId, userId);

        return DeleteResponse.builder()
                .resourceId(taskId)
                .isSuccess(true)
                .message("Task has been deleted successfully.")
                .build();
    }

    public AppResponse getTaskDefaulterMembers(String taskId, String roomId, String userId) throws AccessDeniedException, ResourceNotFoundException {
        commonUtils.checkIfUserIsAModerator(userId, roomId);
        List<MemberResponse> taskDefaulterMembers = taskService.getTaskDefaulterMembers(taskId);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(taskDefaulterMembers)
                .message("Task defaulted members have been fetched successfully.")
                .build();
    }

    public AppResponse handleTaskExpiredEvent(TaskEvent event) {
        taskService.handleTaskExpiredEvent(event);

        return AppResponse.builder()
                .source(TASK)
                .isSuccess(true)
                .response(null)
                .message("Task has been updated successfully.")
                .build();
    }

}
