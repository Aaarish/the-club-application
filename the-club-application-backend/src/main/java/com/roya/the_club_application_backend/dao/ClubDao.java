package com.roya.the_club_application_backend.dao;

import com.roya.the_club_application_backend.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDao extends JpaRepository<Club, String> {
}
