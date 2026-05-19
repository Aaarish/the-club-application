package com.roya.the_club_application_backend.controllers;

import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import com.roya.the_club_application_backend.services.facade.AppUserFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserFacadeService userFacadeService;

    @GetMapping("/")
    public ResponseEntity<AppResponse> getUser(String userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userFacadeService.getUser(userId));
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppResponse> getUserByEmail(String email) throws ResourceNotFoundException {
        return ResponseEntity.ok(userFacadeService.getUserByEmail(email));
    }

    @GetMapping("/{phone}")
    public ResponseEntity<AppResponse> getUserByPhone(String phone) throws ResourceNotFoundException {
        return ResponseEntity.ok(userFacadeService.getUserByPhone(phone));
    }

    @GetMapping("/clubs")
    public ResponseEntity<AppResponse> getClubsOfUser(String userId) {
        return ResponseEntity.ok(userFacadeService.getClubsOfUser(userId));
    }

    @GetMapping("/rooms")
    public ResponseEntity<AppResponse> getRoomsOfUser(String userId) {
        return ResponseEntity.ok(userFacadeService.getRoomsOfUser(userId));
    }

    @GetMapping("/tasks")
    public ResponseEntity<AppResponse> getTasksOfUser(String userId) {
        return ResponseEntity.ok(userFacadeService.getTasksOfUser(userId));
    }

}
