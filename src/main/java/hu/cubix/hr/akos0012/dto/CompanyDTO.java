package hu.cubix.hr.akos0012.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

public record CompanyDTO(
        @JsonView(Views.BaseData.class)
        long id,

        @JsonView(Views.BaseData.class)
        @NotEmpty String registrationNumber,

        @JsonView(Views.BaseData.class)
        @NotEmpty String name,

        @JsonView(Views.BaseData.class)
        String address,

        Set<EmployeeDTO> employees
) {
    public CompanyDTO(long id, @NotEmpty String registrationNumber, @NotEmpty String name, String address) {
        this(id, registrationNumber, name, address, new HashSet<>());
    }
}
