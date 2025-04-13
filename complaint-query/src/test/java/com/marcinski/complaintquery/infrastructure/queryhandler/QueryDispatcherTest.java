package com.marcinski.complaintquery.infrastructure.queryhandler;

import com.marcinski.complaintquery.infrastructure.queryhandler.exception.QueryHandlerNotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryDispatcherTest {

    @Mock
    private Set<QueryHandler<? extends BaseQuery, ? extends QueryResponse>> handlers;

    @InjectMocks
    private QueryDispatcher<BaseQuery> queryDispatcher;

    @Test
    public void testSendWithValidQueryHandler() {
        BaseQuery validQuery = new TestQuery();
        var mockHandler = mock(QueryHandler.class);
        when(mockHandler.getQueryClass()).thenReturn(validQuery.getClass());
        when(mockHandler.handle(validQuery)).thenReturn(new TestQueryResponse());

        when(handlers.stream())
                .thenReturn(Stream.of(mockHandler));

        QueryResponse response = queryDispatcher.send(validQuery);

        assertNotNull(response);
    }

    @Test
    public void testSendWithInvalidQueryHandler() {
        BaseQuery invalidQuery = new TestQuery();
        when(handlers.stream())
                .thenReturn(Stream.empty());

        assertThrows(QueryHandlerNotImplementedException.class, () -> queryDispatcher.send(invalidQuery));
    }

    static class TestQuery extends BaseQuery {
    }

    static class TestQueryResponse extends QueryResponse {
    }
}