package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    private String memberId;
    private String userId;
    private String roomId;
    private String clubId;
    private LocalDateTime joinedAt;
    private String memberDegree;
    private int numOfMissedTasksInRow;
    private int numOfMissedTasksInTotal;

}
