package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntityRepository;
import com.marcinski.complaintquery.infrastructure.queryhandler.exception.ComplaintNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class FindSingleQueryHandler implements QueryHandler<FindComplaintByIdQuery, SingleComplaintQueryResponse> {

    private final ComplaintEntityRepository complaintRepository;

    @Override
    @Transactional(readOnly = true)
    public SingleComplaintQueryResponse handle(FindComplaintByIdQuery query) {
        String id = query.getId();
        ComplaintEntity complaint = complaintRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ComplaintNotFoundException(id));
        return new SingleComplaintQueryResponse(complaint);
    }

    @Override
    public Class<FindComplaintByIdQuery> getQueryClass() {
        return FindComplaintByIdQuery.class;
    }
}
