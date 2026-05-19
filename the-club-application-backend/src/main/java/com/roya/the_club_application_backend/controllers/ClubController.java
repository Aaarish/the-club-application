package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.auth.AuthUser;
import com.roya.the_club_application_backend.dto.requests.ClubRequest;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.global.responses.DeleteResponse;
import com.roya.the_club_application_backend.services.facade.ClubFacadeService;
import com.roya.the_club_application_backend.services.facade.RoomFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
@Slf4j
public class ClubController {
    private final ClubFacadeService clubFacadeService;


    @PostMapping
    public ResponseEntity<AppResponse> createClub(@RequestBody ClubRequest request, @AuthenticationPrincipal AuthUser user) throws ResourceNotFoundException {
        log.info("club creation is initiated by: {}", user.getAppUser().getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(clubFacadeService.createClub(request, user.getAppUser()));
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<AppResponse> getClub(@PathVariable String clubId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        log.info("club fetching is initiated by: {}", user.getAppUser().getUserId());
        return ResponseEntity.ok(clubFacadeService.getClub(clubId, user.getAppUser()));
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<AppResponse> updateClub(@PathVariable String clubId, @RequestBody ClubRequest request, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        log.info("club update is initiated by: {}", user.getAppUser().getUserId());
        return ResponseEntity.ok(clubFacadeService.updateClub(clubId, request, user.getAppUser()));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<DeleteResponse> deleteClub(@PathVariable String clubId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
        log.info("club deletion is initiated by: {}", user.getAppUser().getUserId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clubFacadeService.deleteClub(clubId, user.getAppUser()));
    }

//    @GetMapping("/{clubId}/members")
//    public ResponseEntity<AppResponse> getClubMembers(@PathVariable String clubId, @AuthenticationPrincipal AuthUser user) throws AccessDeniedException, ResourceNotFoundException {
//        return ResponseEntity.ok(clubFacadeService.getClubMembers(clubId, user.getAppUser().getUserId()));
//    }

}
