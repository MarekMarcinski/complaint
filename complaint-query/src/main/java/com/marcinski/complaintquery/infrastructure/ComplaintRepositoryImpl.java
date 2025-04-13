package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.domain.Complaint;
import com.marcinski.complaintquery.domain.ComplaintRepository;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ComplaintRepositoryImpl implements ComplaintRepository {

    private final ComplaintMapper complaintMapper;
    private final ComplaintEntityRepository complaintEntityRepository;

    @Override
    public Optional<Complaint> findById(UUID id) {
        Optional<ComplaintEntity> byId = complaintEntityRepository.findById(id);
        return byId.map(complaintMapper::mapToDomain);
    }

    @Override
    public Complaint save(Complaint complaint) {
        ComplaintEntity complaintEntity = complaintMapper.map(complaint);
        ComplaintEntity saved = complaintEntityRepository.save(complaintEntity);
        return complaintMapper.mapToDomain(saved);
    }
}
