package com.marcinski.complaintquery.domain;

import com.marcinski.complaintquery.domain.event.ComplaintContentsChangedEvent;
import com.marcinski.complaintquery.domain.event.ComplaintCreatedEvent;
import com.marcinski.complaintquery.domain.geo.CityLocator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintEventHandler {

    private final ComplaintRepository complaintRepository;
    private final ComplaintCommandMapper complaintCommandMapper;
    private final CityLocator cityLocator;

    public void on(ComplaintCreatedEvent event) {
        UUID id = UUID.fromString(event.getId());
        Optional<Complaint> byId = complaintRepository.findById(id);
        Complaint complaint;
        if (byId.isPresent()) {
            log.debug("Complaint with id {} already exists. Incrementing reportCounter.", id);
            complaint = byId.get();
            complaint.incrementCounter();
        } else {
            log.debug("Complaint with id {} does not exist. Creating a new one.", id);
            String country = cityLocator.getCityByIp(event.getIpAddress());
            complaint = complaintCommandMapper.map(event, country);
        }
        complaintRepository.save(complaint);
    }

    public void on(ComplaintContentsChangedEvent event) {
        UUID id = UUID.fromString(event.getId());
        Optional<Complaint> byId = complaintRepository.findById(id);
        if (byId.isPresent()) {
            Complaint complaint = byId.get();
            complaint.setContents(event.getContents());
            log.debug("Complaint with id {} has changed contents.", id);
            complaintRepository.save(complaint);
        } else {
            log.debug("Complaint with id {} does not exist. Changes not applied", id);
        }
    }
}
