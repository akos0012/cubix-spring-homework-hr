package hu.cubix.hr.akos0012.controller;


import hu.cubix.hr.akos0012.dto.TimeOffRequestDatesDTO;
import hu.cubix.hr.akos0012.dto.TimeOffRequestDTO;
import hu.cubix.hr.akos0012.dto.TimeOffRequestFilterDTO;
import hu.cubix.hr.akos0012.mapper.TimeOffRequestMapper;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import hu.cubix.hr.akos0012.service.timeOffRequest.TimeOffRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<TimeOffRequestDTO> findSortedTimeOffRequests(@SortDefault("startDate") Pageable pageable, @RequestBody TimeOffRequestFilterDTO filterDTO) {
        List<TimeOffRequest> requests = timeOffRequestService.findRequestsByExample(pageable, filterDTO).getContent();
        return timeOffRequestMapper.timeOffRequestDtos(requests);
    }

    @GetMapping("/id/{id}")
    public TimeOffRequestDTO findById(@PathVariable long id) {
        TimeOffRequest timeOffRequest = timeOffRequestService.findById(id);
        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PostMapping()
    public TimeOffRequestDTO createRequest(@RequestBody @Valid TimeOffRequestDatesDTO datesDTO) {

        TimeOffRequest timeOffRequest = timeOffRequestService.create(datesDTO.startDate(), datesDTO.endDate());

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PutMapping("/{requestID}")
    public TimeOffRequestDTO updateRequest(@PathVariable long requestID, @RequestBody @Valid TimeOffRequestDatesDTO datesDTO) {
        TimeOffRequest timeOffRequest = timeOffRequestService.update(requestID, datesDTO.startDate(), datesDTO.endDate());

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }

    @PutMapping("/judge")
    public TimeOffRequestDTO judgeRequest(@RequestParam long requestID, @RequestParam boolean accept) {
        TimeOffRequest timeOffRequest = timeOffRequestService.judgeRequest(requestID, accept);

        if (timeOffRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return timeOffRequestMapper.timeOffRequestDto(timeOffRequest);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        boolean isDeleted = timeOffRequestService.deleteRequest(id);
        if (!isDeleted)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
