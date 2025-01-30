package hu.cubix.hr.akos0012.mapper;

import hu.cubix.hr.akos0012.dto.TimeOffRequestDTO;
import hu.cubix.hr.akos0012.model.TimeOffRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeOffRequestMapper {

    @Mapping(target = "employeeID", source = "employee.id")
    @Mapping(target = "managerID", source = "manager.id")
    TimeOffRequestDTO timeOffRequestDto(TimeOffRequest timeOffRequest);

    @Mapping(target = "employeeID", source = "employee.id")
    @Mapping(target = "managerID", source = "manager.id")
    List<TimeOffRequestDTO> timeOffRequestDtos(List<TimeOffRequest> timeOffRequests);

    TimeOffRequest dtoToTimeOffRequest(TimeOffRequestDTO timeOffRequestDTO);

//    default Page<TimeOffRequestDTO> pagedTimeOffRequestDto(Page<TimeOffRequest> pagedTimeOffRequest) {
//        return pagedTimeOffRequest.map(this::timeOffRequestDto);
//    }
}
