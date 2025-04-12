package com.marcinski.complaintcommand.api;

import com.marcinski.complaintcommand.TestHelper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommandMapperTest {
    private final CommandMapper mapper = Mappers.getMapper(CommandMapper.class);

    @Test
    void testMapToEditComplaintCommand() {
        var editComplaintRequest = TestHelper.buildEditComplaintRequest();
        String id = UUID.randomUUID().toString();
        var mapped = mapper.map(editComplaintRequest, id);
        assertNotNull(mapped);
        assertEquals(id, mapped.getId());
        assertEquals(editComplaintRequest.getContents(), mapped.getContents());
    }

    @Test
    void testMapToCreateComplaintCommand() {
        var command = TestHelper.buildCreateComplaintRequest();
        String localIp = "127.0.0.1";
        var mapped = mapper.map(command, localIp);
        assertNotNull(mapped);
        assertEquals(command.getReporterName(), mapped.getReporterName());
        assertEquals(command.getContents(), mapped.getContents());
        assertEquals(command.getComplaintProductId(), mapped.getComplaintProductId());
        assertEquals(localIp, mapped.getIpAddress());
    }
}