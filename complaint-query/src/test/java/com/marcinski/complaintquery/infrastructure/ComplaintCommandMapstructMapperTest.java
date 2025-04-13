package com.marcinski.complaintquery.infrastructure;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.marcinski.complaintquery.TestHelper.buildComplaintCreatedEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ComplaintCommandMapstructMapperTest {

    private final ComplaintCommandMapstructMapper mapper = Mappers.getMapper(ComplaintCommandMapstructMapper.class);

    @Test
    void testMapComplaintCreatedEvent() {
        var complaintCreatedEvent = buildComplaintCreatedEvent();
        String country = "Poland";
        var mapped = mapper.map(complaintCreatedEvent, country);
        assertNotNull(mapped);
        assertEquals(complaintCreatedEvent.getId(), mapped.getId().toString());
        assertEquals(complaintCreatedEvent.getComplaintProductId(), mapped.getComplaintProductId());
        assertEquals(complaintCreatedEvent.getReporterName(), mapped.getReporterName());
        assertEquals(complaintCreatedEvent.getContents(), mapped.getContents());
        assertEquals(complaintCreatedEvent.getCreatedDate(), mapped.getCreationDate());
        assertEquals(country, mapped.getCountry());
        assertEquals(1, mapped.getReportCounter());
    }
}