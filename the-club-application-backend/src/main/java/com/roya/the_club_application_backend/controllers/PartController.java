package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.dto.requests.PartRequest;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.services.facade.PartFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("clubs/{clubId}/rooms/{roomId}/tasks/{taskId}/parts")
@RequiredArgsConstructor
public class PartController {
    private final PartFacadeService partFacadeService;

    @PostMapping
    public ResponseEntity<AppResponse> addPartToTask(@PathVariable String taskId, @PathVariable String roomId, @RequestBody PartRequest request, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(partFacadeService.addPartToTask(taskId, roomId, request.getNote(), user.getAppUser().getUserId()));
    }

    @GetMapping
    public ResponseEntity<AppResponse> getPartOfMember(String taskId, @PathVariable String roomId, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(partFacadeService.getPartOfMember(taskId, roomId, user.getAppUser().getUserId()));
    }

    @GetMapping("/all")
    public ResponseEntity<AppResponse> getAllPartsForTask(String taskId, String roomId, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(partFacadeService.getAllPartsForTask(taskId, roomId, user.getAppUser().getUserId()));
    }

}
