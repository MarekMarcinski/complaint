package com.marcinski.complaintquery.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "complaints", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"complaintProductId", "reporterName"}, name = "uk_complaint_product_reporter")
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ComplaintEntity {

    @Id
    private UUID id;
    private UUID complaintProductId;
    private String reporterName;
    private String contents;
    private LocalDate creationDate;
    private String country;
    private Integer reportCounter;
}

