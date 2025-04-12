package com.marcinski.complaintquery.domain;

import java.util.Optional;
import java.util.UUID;

public interface ComplaintRepository {
    Optional<Complaint> findById(UUID id);

    Complaint save(Complaint complaint);
}
