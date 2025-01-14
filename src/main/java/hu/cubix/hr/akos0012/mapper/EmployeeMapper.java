package hu.cubix.hr.akos0012.mapper;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO employeeToDto(Employee employee);

    List<EmployeeDTO> employeesToDtos(List<Employee> employees);

    Employee dtoToEmployee(EmployeeDTO employeeDTO);

    List<Employee> dtosToEmployees(List<EmployeeDTO> employeeDTOs);

    default Page<EmployeeDTO> pagedEmployeeToDto(Page<Employee> pagedEmployee) {
        return pagedEmployee.map(this::employeeToDto);
    }
}
