package hu.cubix.hr.akos0012.dto;

import hu.cubix.hr.akos0012.model.RequestStatus;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record TimeOffRequestFilterDTO(RequestStatus status,
                                      String employeePrefix,
                                      String managerPrefix,
                                      LocalDateTime createdStartDate,
                                      LocalDateTime createdEndDate,
                                      LocalDateTime timeOffRequestedStartDate,
                                      LocalDateTime timeOffRequestedEndDate) {
}
