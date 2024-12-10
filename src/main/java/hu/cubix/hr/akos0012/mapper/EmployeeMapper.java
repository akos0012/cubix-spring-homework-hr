package hu.cubix.hr.akos0012.mapper;

import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.model.Company;
import hu.cubix.hr.akos0012.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    public EmployeeDTO employeeToDto(Employee employee);

    public List<EmployeeDTO> employeesToDtos(List<Employee> employees);

    public Employee dtoToEmployee(EmployeeDTO employeeDTO);

    public List<Employee> dtosToEmployees(List<EmployeeDTO> employeeDTOs);
}
