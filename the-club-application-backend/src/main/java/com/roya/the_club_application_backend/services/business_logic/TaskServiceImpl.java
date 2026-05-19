package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dao.TaskDao;
import com.roya.the_club_application_backend.dto.events.TaskEvent;
import com.roya.the_club_application_backend.dto.requests.TaskRequest;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.entities.Task;
import com.roya.the_club_application_backend.global.GlobalDao;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;
    private final GlobalDao globalDao;

    @Override
    public TaskResponse createTask(String roomId, TaskRequest request, String userId) {
        Task task = new Task(roomId, request.getDescription(), userId, request.getToBeCompletedAt());
        Task savedTask = taskDao.save(task);

        return savedTask.toResponse();
    }

    @Override
    public TaskResponse getTask(String taskId) throws ResourceNotFoundException {
        Task task = getTaskById(taskId);
        return task.toResponse();
    }

    @Override
    public TaskResponse updateTask(String taskId, TaskRequest request) throws ResourceNotFoundException {
        Task task = getTaskById(taskId);

        if (request.getDescription() != null) task.changeDesc(request.getDescription());
        if (request.getToBeCompletedAt() != null) task.changeExpiry(request.getToBeCompletedAt());

        Task updatedTask = taskDao.save(task);
        return updatedTask.toResponse();
    }

    @Override
    public void deleteTask(String taskId, String userId) throws ResourceNotFoundException {
        Task task = getTaskById(taskId);
        if (!task.getTaskCreatorId().equals(userId)) throw new RuntimeException("Only task owner can delete the task");

        taskDao.delete(task);
    }

    @Override
    public List<MemberResponse> getTaskDefaulterMembers(String taskId) {
        // join query on parts, tasks, rooms and members to fetch room members of the task who do not have a part in that task.

        String sql = """
            SELECT
                rm.member_id,
                rm.room_id,
                rm.user_id,
                rm.joined_at,
                rm.member_degree,
                rm.num_of_missed_tasks_in_a_row,
                rm.num_of_missed_tasks_in_total
            FROM TASKS t
            JOIN ROOMS r         ON t.room_id   = r.room_id
            JOIN ROOM_MEMBERS rm ON r.room_id   = rm.room_id
            LEFT JOIN PARTS p    ON t.task_id   = p.task_id
                                AND rm.member_id = p.member_id
            WHERE t.task_id  = ?
              AND p.part_id IS NULL
              AND t.to_be_completed_at < CURRENT_TIMESTAMP
            """;

        return globalDao.query(sql, globalDao::mapRowToMemberResponse, taskId);
    }

    @Override
    public void handleTaskExpiredEvent(TaskEvent event) {
        if (event == null || event.getTaskId() == null) {
            throw new IllegalArgumentException("TaskEvent or taskId must not be null");
        }

        String sql = """
            UPDATE ROOM_MEMBERS rm
            JOIN ROOMS r      ON rm.room_id  = r.room_id
            JOIN TASKS t      ON r.room_id   = t.room_id
            LEFT JOIN PARTS p ON t.task_id   = p.task_id
                             AND rm.member_id = p.member_id
            SET rm.num_of_missed_tasks_in_a_row = rm.num_of_missed_tasks_in_a_row + 1,
                rm.num_of_missed_tasks_in_total  = rm.num_of_missed_tasks_in_total + 1
            WHERE t.task_id            = ?
              AND p.part_id           IS NULL
              AND t.to_be_completed_at < CURRENT_TIMESTAMP
            """;

        int updatedRows = globalDao.update(sql, event.getTaskId());

        if (updatedRows == 0) {
            log.warn("No defaulter members found for taskId: {}", event.getTaskId());
        } else {
            log.info("Updated {} defaulter member(s) for taskId: {}", updatedRows, event.getTaskId());
        }
    }

    private Task getTaskById(String taskId) throws ResourceNotFoundException {
        return taskDao.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.TASK, "Task with id: " + taskId + " does not exist."));
    }

}
