package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dto.events.TaskEvent;

public interface TaskEventService {

//    void sendNotification(String userId, String message);

//    void sendNotificationToAllMembers(String message);

    void publishTaskExpiredEvent(String taskId, TaskEvent event);

}
