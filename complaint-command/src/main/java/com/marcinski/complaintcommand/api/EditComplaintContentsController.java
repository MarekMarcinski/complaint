package com.marcinski.complaintcommand.api;

import com.marcinski.complaintcommand.api.dto.BaseResponse;
import com.marcinski.complaintcommand.api.dto.EditComplaintRequest;
import com.marcinski.complaintcommand.domain.ComplaintFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class EditComplaintContentsController {

    private final ComplaintFacade complaintFacade;
    private final CommandMapper commandMapper;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> restoreReadDb(@PathVariable(value = "id") String id,
                                                      @Valid @RequestBody EditComplaintRequest editComplaintRequest) {
        var command = commandMapper.map(editComplaintRequest, id);
        complaintFacade.handle(command);
        return new ResponseEntity<>(new BaseResponse(id), HttpStatus.ACCEPTED);
    }
}
