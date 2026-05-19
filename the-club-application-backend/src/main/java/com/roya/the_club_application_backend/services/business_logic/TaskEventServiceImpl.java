package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dao.TaskDao;
import com.roya.the_club_application_backend.dto.events.TaskEvent;
import com.roya.the_club_application_backend.entities.Part;
import com.roya.the_club_application_backend.entities.Task;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskEventServiceImpl implements TaskEventService {
    private final TaskDao taskDao;


//    private void handleTaskExpiredEvent(TaskEvent event) throws ResourceNotFoundException {
//        Task task = taskDao.findById(event.getTaskId())
//                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.TASK, "Task with this id does not exist"));
//
//        List<Part> parts = task.getParts();
//    }

    @Override
    public void publishTaskExpiredEvent(String taskId, TaskEvent event) {

    }
}
