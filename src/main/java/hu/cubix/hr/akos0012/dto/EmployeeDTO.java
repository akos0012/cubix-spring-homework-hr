package hu.cubix.hr.akos0012.dto;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;


public record EmployeeDTO(long id, @NotEmpty String name, String jobTitle, @Positive int salary,
                          @PastOrPresent LocalDateTime dateOfStartWork
) {
}

