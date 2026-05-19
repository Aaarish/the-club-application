package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.dto.requests.RoomRequest;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.services.facade.RoomFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/clubs/{clubId}/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomFacadeService roomFacadeService;

    // fix all controller methods to get userId from security context instead of request param, and remove userId from method params

    @PostMapping
    public ResponseEntity<AppResponse> createRoom(@PathVariable String clubId, @RequestBody RoomRequest request, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException {
        return ResponseEntity.ok(roomFacadeService.createRoom(clubId, request, user.getAppUser()));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<AppResponse> getRoom(@PathVariable String roomId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(roomFacadeService.getRoom(roomId, user.getAppUser().getUserId()));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<AppResponse> updateRoom(@PathVariable String roomId, @RequestBody RoomRequest request, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(roomFacadeService.updateRoom(roomId, request, user.getAppUser()));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<DeleteResponse> deleteRoom(@PathVariable String roomId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(roomFacadeService.deleteRoom(roomId, user.getAppUser()));
    }

    @GetMapping("/{roomId}/members")
    public ResponseEntity<AppResponse> getRoomMembers(@PathVariable String roomId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.ok(roomFacadeService.getRoomMembers(roomId, user.getAppUser().getUserId()));
    }

}
