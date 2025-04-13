package com.marcinski.complaintquery.infrastructure.queryhandler;

interface QueryHandler<T extends BaseQuery, O extends QueryResponse> {
    O handle(T query);

    Class<T> getQueryClass();
}
