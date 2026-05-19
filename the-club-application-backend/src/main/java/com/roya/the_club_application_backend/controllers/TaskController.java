package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.dto.requests.TaskRequest;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.services.facade.TaskFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/clubs/{clubId}/rooms/{roomId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskFacadeService taskFacadeService;

    @PostMapping
    public ResponseEntity<AppResponse> createTask(@PathVariable String roomId, @RequestBody TaskRequest request, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(taskFacadeService.createTask(roomId, request, user.getAppUser().getUserId()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<AppResponse> getTask(@PathVariable String roomId, @PathVariable String taskId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(taskFacadeService.getTask(roomId, taskId, user.getAppUser().getUserId()));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<AppResponse> updateTask(@PathVariable String roomId, @PathVariable String taskId, @RequestBody TaskRequest request, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(taskFacadeService.updateTask(roomId, taskId, request, user.getAppUser().getUserId()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<DeleteResponse> deleteTask(@PathVariable String roomId, @PathVariable String taskId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(taskFacadeService.deleteTask(roomId, taskId, user.getAppUser().getUserId()));
    }

    @GetMapping("/defaulters")
    public ResponseEntity<AppResponse> getDefaultedMembersForTask(@PathVariable String taskId, @PathVariable String roomId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(taskFacadeService.getTaskDefaulterMembers(taskId, roomId, user.getAppUser().getUserId()));
    }

}
