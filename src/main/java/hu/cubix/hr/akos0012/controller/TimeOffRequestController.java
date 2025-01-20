package hu.cubix.hr.akos0012.controller;


import hu.cubix.hr.akos0012.dto.TimeOffRequestDatesDTO;
import hu.cubix.hr.akos0012.dto.TimeOffRequestDTO;
import hu.cubix.hr.akos0012.dto.TimeOffRequestFilterSortDTO;
import hu.cubix.hr.akos0012.mapper.TimeOffRequestMapper;
import hu.cubix.hr.akos0012.model.RequestStatus;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import hu.cubix.hr.akos0012.service.timeOffRequest.TimeOffRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-off-request")
public class TimeOffRequestController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @Autowired
    private TimeOffRequestMapper timeOffRequestMapper;

    @GetMapping
    public List<TimeOffRequestDTO> findAll() {
        return timeOffRequestMapper.timeOffRequestDtos(timeOffRequestService.findAll());
    }

    @GetMapping("/sort-filter")
    public Page<TimeOffRequestDTO> findSortedTimeOffRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) String employeePrefix,
            @RequestParam(required = false) String managerPrefix,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdStartDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdEndDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeOffRequestedStartDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeOffRequestedEndDate
    ) {
        Page<TimeOffRequest> requests = timeOffRequestService.findRequestsByExample(page, size, sortBy, sortDirection, status, employeePrefix, managerPrefix, createdStartDate, createdEndDate, timeOffRequestedStartDate, timeOffRequestedEndDate);
        return timeOffRequestMapper.pagedTimeOffRequestDto(requests);
    }

//    @PostMapping("/filter")
//    public List<TimeOffRequestDTO> findRequests(@RequestBody TimeOffRequestFilterSortDTO timeOffRequestFilterSortDTO) {
//        List<TimeOffRequest> requests = timeOffRequestService.findRequestsByExample(timeOffRequestFilterSortDTO);
//        return timeOffRequestMapper.timeOffRequestDtos(requests);
//    }

    @GetMapping("/id/{id}")
    public TimeOffRequestDTO findById(@PathVariable long id) {
        TimeOffRequest timeOffRequest = timeOffRequestService.findById(id);
        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PostMapping("/{employeeID}")
    public TimeOffRequestDTO createRequest(@PathVariable long employeeID, @RequestBody @Valid TimeOffRequestDatesDTO datesDTO) {

        TimeOffRequest timeOffRequest = timeOffRequestService.create(employeeID, datesDTO.startDate(), datesDTO.endDate());

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PutMapping("/{requestID}")
    public TimeOffRequestDTO updateRequest(@PathVariable long requestID, @RequestBody @Valid TimeOffRequestDatesDTO datesDTO) {
        TimeOffRequest timeOffRequest = timeOffRequestService.update(requestID, datesDTO.startDate(), datesDTO.endDate());

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PutMapping("/judge/{managerID}")
    public TimeOffRequestDTO judgeRequest(@PathVariable long managerID, @RequestParam long requestID, @RequestParam boolean accept) {
        TimeOffRequest timeOffRequest = timeOffRequestService.judgeRequest(managerID, requestID, accept);

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        boolean isDeleted = timeOffRequestService.deleteRequest(id);
        if (!isDeleted)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PENDING requests can be deleted.");
    }

}
