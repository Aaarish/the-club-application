package com.roya.the_club_application_backend.entities;

import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import jakarta.persistence.*;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MEMBERS")
@Getter
public class Member implements Persistable<String> {
    @Id
    private String memberId;
    private String userId;
    private String roomId;
    private String clubId;
    private LocalDateTime joinedAt;

    public enum MemberDegree {
        MEMBER(1),             // normal members by default
        MODERATOR(2),          // moderators can manage the room including adding/removing members, editing tasks, and changing room rules
        CLUB_MANAGER(3),       // club managers can manage the club including creating/deleting rooms, and managing moderators
        CLUB_OWNER(4)          // club owners can manage the club including creating/deleting rooms/club, managing moderators, and managing club managers
        ;

        private int memberDegree;

        private MemberDegree(int memberDegree) {
            this.memberDegree = memberDegree;
        }

        public int getMemberDegree() {
            return memberDegree;
        }
    }

    @Enumerated(EnumType.STRING)
    private MemberDegree memberDegree;
    private int numOfMissedTasksInRow;
    private int numOfMissedTasksInTotal;

    public Member() {
    }

    public Member(String userId, String roomId, String clubId, MemberDegree memberDegree) {
        this.memberId = UUID.randomUUID().toString();
        this.userId = userId;
        this.roomId = roomId;
        this.clubId = clubId;
        this.memberDegree = memberDegree;
        this.joinedAt = LocalDateTime.now();
    }

    public void changeMemberDegree(MemberDegree memberDegree) {
        this.memberDegree = memberDegree;
    }

    public void promoteMemberToModerator() {
        if (this.memberDegree == MemberDegree.MEMBER) {
            this.memberDegree = MemberDegree.MODERATOR;
        }
    }

    public void promoteModeratorToClubManager() {
        if (this.memberDegree == MemberDegree.MODERATOR) {
            this.memberDegree = MemberDegree.CLUB_MANAGER;
        }
    }

    public void demoteModeratorToMember() {
        if (this.memberDegree == MemberDegree.MODERATOR) {
            this.memberDegree = MemberDegree.MEMBER;
        }
    }

    public void demoteClubManagerToModerator() {
        if (this.memberDegree == MemberDegree.CLUB_MANAGER) {
            this.memberDegree = MemberDegree.MODERATOR;
        }
    }

    public void incrementNumOfMissedTasksInARow() {
        this.numOfMissedTasksInRow++;
        this.numOfMissedTasksInTotal++;
    }

    public void resetNumOfMissedTasksInARow() {
        this.numOfMissedTasksInRow = 0;
    }

    public String userForMember() {
        return this.userId;
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .memberId(this.getMemberId())
                .roomId(this.roomId)
                .userId(this.userId)
                .joinedAt(this.getJoinedAt())
                .memberDegree(this.getMemberDegree().getMemberDegree())
                .numOfMissedTasksInRow(this.getNumOfMissedTasksInRow())
                .numOfMissedTasksInTotal(this.getNumOfMissedTasksInTotal())
                .build();
    }

    @Transient
    private boolean isNew = true;

    @Override
    public @Nullable String getId() {
        return this.memberId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

}
