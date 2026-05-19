package com.roya.the_club_application_backend.global;

import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import com.roya.the_club_application_backend.dto.responses.MemberResponse;
import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import com.roya.the_club_application_backend.entities.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalDao {
    private final JdbcTemplate jdbcTemplate;

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        return jdbcTemplate.queryForObject(sql, rowMapper, args);
    }

    public int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    public Room mapRowToRoom(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();

        room.setRoomId(rs.getString("room_id"));
        room.setRoomId(rs.getString("room_id"));
        room.setName(rs.getString("name"));
        room.setDescription(rs.getString("description"));
        room.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        room.setClubId(rs.getString("club_id"));

        return room;
    }

    public MemberResponse mapRowToMemberResponse(ResultSet rs, int rowNum) throws SQLException {
        return MemberResponse.builder()
                .memberId(rs.getString("member_id"))
                .roomId(rs.getString("room_id"))
                .userId(rs.getString("user_id"))
                .joinedAt(rs.getTimestamp("joined_at") != null
                        ? rs.getTimestamp("joined_at").toLocalDateTime()
                        : null)
                .memberDegree(rs.getInt("member_degree"))
                .numOfMissedTasksInRow(rs.getInt("num_of_missed_tasks_in_a_row"))
                .numOfMissedTasksInTotal(rs.getInt("num_of_missed_tasks_in_total"))
                .build();
    }

    public RoomResponse mapRowToRoomResponse(ResultSet resultSet, int rowNum) throws SQLException {
        return RoomResponse.builder()
                .roomId(resultSet.getString("room_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("desc"))
                .clubId(resultSet.getString("club_id"))
                .createdAt(resultSet.getTimestamp("created_at") != null
                        ? resultSet.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }

    public ClubResponse mapRowToClub(ResultSet resultSet, int rowNum) throws SQLException {
        return ClubResponse.builder()
                .clubId(resultSet.getString("club_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("desc"))
                .createdAt(resultSet.getTimestamp("created_at") != null
                        ? resultSet.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }

    public TaskResponse mapRowToTask(ResultSet resultSet, int rowNum) throws SQLException {
        return TaskResponse.builder()
                .taskId(resultSet.getString("task_id"))
                .description(resultSet.getString("desc"))
                .toBeCompletedAt(resultSet.getTimestamp("to_be_completed_at") != null
                        ? resultSet.getTimestamp("to_be_completed_at").toLocalDateTime()
                        : null)
                .roomId(resultSet.getString("room_id"))
                .taskCreatorId(resultSet.getString("task_creator_id"))
                .createdAt(resultSet.getTimestamp("created_at") != null
                        ? resultSet.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }

}
