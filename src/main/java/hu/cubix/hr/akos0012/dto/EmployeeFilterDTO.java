package hu.cubix.hr.akos0012.dto;

import java.time.LocalDate;

public record EmployeeFilterDTO(long employeeID, String employeePrefix, String companyPrefix, String position,
                                int salary,
                                LocalDate entryDate) {
}
