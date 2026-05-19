package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dto.events.TaskEvent;
import com.roya.the_club_application_backend.dto.requests.TaskRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(String roomId, TaskRequest request, String userId);

    TaskResponse getTask(String taskId) throws ResourceNotFoundException;

    TaskResponse updateTask(String taskId, TaskRequest request) throws ResourceNotFoundException;

    void deleteTask(String taskId, String userId) throws ResourceNotFoundException;

    // needs to be called whenever a task is opened to mark task-defaulters as red (green: doers, yellow: in-progress)
    List<MemberResponse> getTaskDefaulterMembers(String taskId);

    // needs to be called when the cron-job detects task-expiry to update member's meta-data, to turn task status to red and send notifications to task-defaulters and task-doers.
    void handleTaskExpiredEvent(TaskEvent event);

}
