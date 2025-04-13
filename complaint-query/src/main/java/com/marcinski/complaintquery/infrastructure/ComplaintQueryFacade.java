package com.marcinski.complaintquery.infrastructure;

import com.marcinski.complaintquery.infrastructure.dto.ComplaintResponse;
import com.marcinski.complaintquery.infrastructure.dto.ListComplaintResponse;
import com.marcinski.complaintquery.infrastructure.entity.ComplaintEntity;
import com.marcinski.complaintquery.infrastructure.queryhandler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintQueryFacade {

    private final QueryDispatcher<BaseQuery> queryDispatcher;
    private final ComplaintMapper mapper;


    public ListComplaintResponse getAllComplaints(FindAllComplaintQuery query) {
        ListComplaintQueryResponse response = (ListComplaintQueryResponse) queryDispatcher.send(query);
        Page<ComplaintEntity> complaintPage = response.getComplaints();
        List<ComplaintResponse> list = complaintPage
                .stream()
                .map(mapper::map)
                .toList();
        return new ListComplaintResponse(new PageImpl<>(list, complaintPage.getPageable(), complaintPage.getTotalElements()));
    }

    public ComplaintResponse getComplaintById(FindComplaintByIdQuery query) {
        SingleComplaintQueryResponse response = (SingleComplaintQueryResponse) queryDispatcher.send(query);
        ComplaintEntity complaint = response.getComplaint();
        return mapper.map(complaint);
    }
}
