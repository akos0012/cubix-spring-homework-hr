package hu.cubix.hr.akos0012.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TimeOffRequestDatesDTO(
        @NotNull
        @FutureOrPresent
        LocalDateTime startDate,

        @NotNull
        @Future
        LocalDateTime endDate
) {
}
