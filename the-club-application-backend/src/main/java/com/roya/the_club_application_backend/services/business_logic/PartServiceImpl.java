package com.roya.the_club_application_backend.services.business_logic;

import com.roya.the_club_application_backend.dao.PartDao;
import com.roya.the_club_application_backend.dto.responses.PartResponse;
import com.roya.the_club_application_backend.entities.Part;
import com.roya.the_club_application_backend.global.OperationLevel;
import com.roya.the_club_application_backend.global.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {
    private final PartDao partDao;

    @Override
    public List<PartResponse> getAllPartsForTask(String taskId) {
        return partDao.findByTaskId(taskId).stream()
                .map(Part::toResponse)
                .toList();
    }

    @Override
    public PartResponse getPartOfMember(String taskId, String memberId) throws ResourceNotFoundException {
        return partDao.findByTaskIdAndMemberId(taskId, memberId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.TASK, "Part for task with id: " + taskId + " and member with id: " + memberId + " does not exist."))
                .toResponse();
    }

    @Override
    public PartResponse addPartToTask(String taskId, String memberId, Object proof, String note) {
        Part part = new Part(taskId, memberId, proof, note);
        Part savedPart = partDao.save(part);
        return savedPart.toResponse();
    }

    private Part getPartById(String partId) throws ResourceNotFoundException {
        return partDao.findById(partId)
                .orElseThrow(() -> new ResourceNotFoundException(OperationLevel.TASK, "Part with id: " + partId + " does not exist."));
    }

}
