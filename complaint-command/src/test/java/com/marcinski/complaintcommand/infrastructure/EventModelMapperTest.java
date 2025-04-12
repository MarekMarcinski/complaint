package com.marcinski.complaintcommand.infrastructure;

import com.marcinski.complaintcommand.TestHelper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventModelMapperTest {

    private final EventModelMapper mapper = Mappers.getMapper(EventModelMapper.class);

    @Test
    void testMapToEventModelDocument() {
        var eventModel = TestHelper.buildEventEditModel();
        var map = mapper.map(eventModel);
        assertNotNull(map);
        assertNotNull(map.getId());
        assertNotNull(map.getTimestamp());
        assertNotNull(map.getAggregateIdentifier());
        assertEquals("Type", map.getAggregateType());
        assertEquals(1, map.getVersion());
        assertNotNull(map.getEventData());
    }

    @Test
    void testMapToEventDocumentDocument() {
        var eventModel = TestHelper.buildEventEditModelDocument();
        var map = mapper.map(eventModel);
        assertNotNull(map);
        assertNotNull(map.getId());
        assertNotNull(map.getTimestamp());
        assertNotNull(map.getAggregateIdentifier());
        assertEquals("Type", map.getAggregateType());
        assertEquals(1, map.getVersion());
        assertNotNull(map.getEventData());
    }
}