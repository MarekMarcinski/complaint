package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntityRepository;
import com.marcinski.complaintquery.infrastructure.queryhandler.exception.InvalidSortPropertyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.TreeSet;

@Component
@RequiredArgsConstructor
class FindAllQueryHandler implements QueryHandler<FindAllComplaintQuery, ListComplaintQueryResponse> {

    private static final Set<String> ALLOWED_SORT_PROPERTIES = new TreeSet<>(Set.of("creationDate", "reportCounter"));
    private final ComplaintEntityRepository complaintRepository;

    private static void checkSortProperty(Pageable pageable) {
        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORT_PROPERTIES.contains(order.getProperty())) {
                String message = String.format("Invalid sort property: %s. Allowed property: %s",
                        order.getProperty(), ALLOWED_SORT_PROPERTIES);
                throw new InvalidSortPropertyException(message);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ListComplaintQueryResponse handle(FindAllComplaintQuery query) {
        Pageable pageable = query.getPageable();
        checkSortProperty(pageable);
        Page<ComplaintEntity> pagedComplaints = complaintRepository.findAll(pageable);
        return new ListComplaintQueryResponse(pagedComplaints);
    }

    @Override
    public Class<FindAllComplaintQuery> getQueryClass() {
        return FindAllComplaintQuery.class;
    }
}
