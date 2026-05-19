package com.roya.the_club_application_backend.dao;

import com.roya.the_club_application_backend.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartDao extends JpaRepository<Part, String> {
    List<Part> findByTaskId(String taskId);

    Optional<Part> findByTaskIdAndMemberId(String taskId, String memberId);

}
