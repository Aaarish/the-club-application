package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {
    private String memberId;
    private String userId;
    private String roomId;
    private LocalDateTime joinedAt;
    private int memberDegree;
    private int numOfMissedTasksInRow;
    private int numOfMissedTasksInTotal;

}
