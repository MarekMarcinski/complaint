package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import lombok.Value;

@Value
public class SingleComplaintQueryResponse extends QueryResponse {
    ComplaintEntity complaint;
}
