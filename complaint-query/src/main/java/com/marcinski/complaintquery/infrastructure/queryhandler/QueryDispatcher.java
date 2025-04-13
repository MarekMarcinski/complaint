package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.queryhandler.exception.QueryHandlerNotImplementedException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QueryDispatcher<T extends BaseQuery> {
    private final Set<QueryHandler<? extends BaseQuery, ? extends QueryResponse>> handlers;

    public QueryDispatcher(Set<QueryHandler<? extends BaseQuery, ? extends QueryResponse>> handlers) {
        this.handlers = handlers;
    }

    public QueryResponse send(BaseQuery query) {
        var commandHandlerMethod = handlers.stream()
                .filter(h -> h.getQueryClass()
                        .equals(query.getClass()))
                .map(QueryHandler.class::cast)
                .findFirst()
                .orElseThrow(() -> new QueryHandlerNotImplementedException(query.getClass()));
        return commandHandlerMethod.handle(query);
    }
}
