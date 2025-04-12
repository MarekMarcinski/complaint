package com.marcinski.complaintcommand.api;

import com.marcinski.complaintcommand.api.dto.BaseResponse;
import com.marcinski.complaintcommand.api.dto.CreateComplaintRequest;
import com.marcinski.complaintcommand.domain.ComplaintFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class CreateComplaintController {

    private final ComplaintFacade complaintFacade;
    private final CommandMapper commandMapper;

    @PostMapping
    public ResponseEntity<BaseResponse> createComplaint(@Valid @RequestBody CreateComplaintRequest createComplaintRequest,
                                                        HttpServletRequest httpServletRequest) {
        var createComplaintCommand = commandMapper.map(createComplaintRequest, getClientIp(httpServletRequest));

        String id = complaintFacade.handle(createComplaintCommand);
        return new ResponseEntity<>(new BaseResponse(id), HttpStatus.CREATED);
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0]; // First IP in list is original client
        }
        return request.getRemoteAddr();
    }
}
