package com.marcinski.complaintquery.infrastructure.queryhandler.exception;

import com.marcinski.complaintquery.infrastructure.queryhandler.BaseQuery;

public class QueryHandlerNotImplementedException extends RuntimeException {
    public QueryHandlerNotImplementedException(Class<? extends BaseQuery> clazz) {
        super("This query: " + clazz.getSimpleName() + " is not supported.");
    }
}
