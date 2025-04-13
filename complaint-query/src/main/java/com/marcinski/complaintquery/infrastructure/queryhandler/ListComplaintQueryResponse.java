package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import lombok.Value;
import org.springframework.data.domain.Page;

@Value
public class ListComplaintQueryResponse extends QueryResponse {
    Page<ComplaintEntity> complaints;
}
