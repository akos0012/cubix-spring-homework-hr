package hu.cubix.hr.akos0012.dto;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.model.RequestStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TimeOffRequestDTO(
        long id,

        @NotNull
        Long employeeID,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate,

        Long managerID,

        @NotNull
        RequestStatus requestStatus,

        @NotNull
        @PastOrPresent
        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}
