package com.marcinski.complaintquery.api;

import com.marcinski.complaintquery.infrastructure.ComplaintQueryFacade;
import com.marcinski.complaintquery.infrastructure.dto.ComplaintResponse;
import com.marcinski.complaintquery.infrastructure.dto.ListComplaintResponse;
import com.marcinski.complaintquery.infrastructure.queryhandler.FindAllComplaintQuery;
import com.marcinski.complaintquery.infrastructure.queryhandler.FindComplaintByIdQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintQueryFacade complaintQueryFacade;

    @GetMapping
    public ResponseEntity<ListComplaintResponse> getComplaintById(Pageable pageable) {
        log.info("Handle a request to fetch complaints. Page request: {}", pageable);

        var query = new FindAllComplaintQuery(pageable);
        var response = complaintQueryFacade.getAllComplaints(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable String id) {
        log.info("Handle a request to fetch a Complaint by id: {}", id);

        var query = new FindComplaintByIdQuery(id);
        var response = complaintQueryFacade.getComplaintById(query);
        return ResponseEntity.ok(response);
    }
}
