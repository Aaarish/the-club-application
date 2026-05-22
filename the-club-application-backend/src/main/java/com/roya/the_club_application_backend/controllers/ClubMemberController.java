package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.dto.requests.MemberRequest;
import com.roya.the_club_application_backend.global.exceptions.ActionDeniedException;
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
@RequestMapping("/clubs/{clubId}/members")
@RequiredArgsConstructor
public class ClubMemberController {
    private final MemberFacadeService memberFacadeService;

    @PostMapping
    public ResponseEntity<AppResponse> addMember(@PathVariable String clubId, @RequestBody MemberRequest request, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, ActionDeniedException {
        return ResponseEntity.ok(memberFacadeService.addClubMember(clubId, request.getUserIdForMember(), user.getAppUser().getUserId()));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<DeleteResponse> removeClubMember(@PathVariable String clubId, @PathVariable String userIdForMember, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException, ActionDeniedException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberFacadeService.removeClubMember(clubId, userIdForMember, user.getAppUser().getUserId()));
    }

}
