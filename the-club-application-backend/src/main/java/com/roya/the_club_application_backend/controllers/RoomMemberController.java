package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.services.facade.MemberFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/clubs/{clubId}/rooms/{roomId}/members")
@RequiredArgsConstructor
public class RoomMemberController {
    private final MemberFacadeService memberFacadeService;

    @PostMapping
    public ResponseEntity<AppResponse> addMember(@PathVariable String clubId, @PathVariable String roomId, String userIdForMember, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(memberFacadeService.addRoomMember(clubId, roomId, userIdForMember, user.getAppUser().getUserId()));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<AppResponse> getMember(@PathVariable String memberId) throws ResourceNotFoundException {
        return ResponseEntity.ok(memberFacadeService.getRoomMember(memberId));
    }

    @PutMapping("/{memberId}/promote")
    public ResponseEntity<AppResponse> promoteMemberDegree(@PathVariable String roomId, String memberId, String memberDegree, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(memberFacadeService.promoteMemberDegree(roomId, memberId, memberDegree, user.getAppUser().getUserId()));
    }

    @PutMapping("/{memberId}/demote")
    public ResponseEntity<AppResponse> demoteMemberDegree(@PathVariable String roomId, String memberId, String memberDegree, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(memberFacadeService.demoteMemberDegree(roomId, memberId, memberDegree, user.getAppUser().getUserId()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteResponse> removeRoomMember(String roomId, String memberId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberFacadeService.removeRoomMember(roomId, memberId, user.getAppUser().getUserId()));
    }

}
