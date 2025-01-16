package hu.cubix.hr.akos0012.dto;

import hu.cubix.hr.akos0012.model.Position;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;


public record EmployeeDTO(long id, @NotEmpty String name, @Positive int salary, Position position,
                          @PastOrPresent LocalDateTime dateOfStartWork
) {
}

