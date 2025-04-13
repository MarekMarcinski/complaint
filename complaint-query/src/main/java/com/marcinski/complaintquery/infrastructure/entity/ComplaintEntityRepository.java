package com.marcinski.complaintquery.infrastructure.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplaintEntityRepository extends JpaRepository<ComplaintEntity, UUID> {
}
