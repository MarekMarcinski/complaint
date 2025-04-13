package com.marcinski.complaintquery.infrastructure.queryhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindComplaintByIdQuery extends BaseQuery {
    String id;
}
