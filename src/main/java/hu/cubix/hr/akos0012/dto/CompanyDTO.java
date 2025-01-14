package hu.cubix.hr.akos0012.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.cubix.hr.akos0012.model.CompanyForm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
        @NotNull CompanyForm companyForm,

        @JsonView(Views.BaseData.class)
        String address,

        Set<EmployeeDTO> employees
) {
    public CompanyDTO {
        employees = employees != null ? employees : new HashSet<>();
    }
}
