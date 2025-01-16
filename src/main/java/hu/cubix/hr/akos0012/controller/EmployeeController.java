package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.dto.EmployeeFilterDTO;
import hu.cubix.hr.akos0012.mapper.EmployeeMapper;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.service.employee.EmployeeService;
import hu.cubix.hr.akos0012.service.employee.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    SalaryService salaryService;


    //get the percentage of pay raise for that employee
    @PostMapping("/payRaisePercentage")
    public ResponseEntity<Integer> getPayRaisePercentage(@RequestBody Employee employee) {
        if (employee == null) return ResponseEntity.badRequest().build();
        int payRaisePercentage = salaryService.getPercentageOfPayRaise(employee);
        return ResponseEntity.ok(payRaisePercentage);
    }

    //Listing all employees
    //Listing employees with salary higher than a given value passed as query parameter
    //example url: http://localhost:8080/api/employees?salary=10000&job=Rock Analyst&name=Dwayne&startDate=1930-02-14T08:00:00&endDate=1980-02-14T08:00:00
    @GetMapping
    public List<EmployeeDTO> findAll(@RequestParam(required = false) Integer salary,
                                     @RequestParam(required = false) String job,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Employee> filteredEmployees = employeeService.findFilteredEmployees(salary, job, name, startDate, endDate);

        return employeeMapper.employeesToDtos(filteredEmployees);
    }

    //Return an employee with a given id

    @GetMapping("/paged")
    public Page<EmployeeDTO> getPagedEmployees(@RequestParam int page, @RequestParam int size) {
        Page<Employee> pagedEmployees = employeeService.findEmployeesWithPaging(page, size);
        System.out.println("Page number: " + pagedEmployees.getNumber());
        System.out.println("Total pages: " + pagedEmployees.getTotalPages());
        System.out.println("Total elements: " + pagedEmployees.getTotalElements());
        System.out.println("Number of elements: " + pagedEmployees.getNumberOfElements());
        //System.out.println("Content: " + pagedEmployees.getContent());


        return employeeMapper.pagedEmployeeToDto(pagedEmployees);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
        return ResponseEntity.notFound().build();
    }

    //Adding a new employee
    @PostMapping
    public EmployeeDTO create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.create(employee);

        if (savedEmployee == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return employeeMapper.employeeToDto(savedEmployee);
    }

    @PostMapping("/filter")
    public List<EmployeeDTO> findEmployees(@RequestBody EmployeeFilterDTO employeeFilterDTO) {
        List<Employee> employees = employeeService.findEmployeesByExample(employeeFilterDTO);
        return employeeMapper.employeesToDtos(employees);
    }

    //Modifying an existing employee
    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable long id, @RequestBody @Valid EmployeeDTO employeeDTO) {

        //if (employeeDTO.id() != id) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        employeeDTO = new EmployeeDTO(id, employeeDTO.name(), employeeDTO.salary(), employeeDTO.position(), employeeDTO.dateOfStartWork());

        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
        Employee updatedEmployee = employeeService.update(employee);


        if (updatedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(updatedEmployee);
    }

    @Transactional
    @PutMapping("/updateMinSalaryByPosition")
    public String updateMinSalaryByPosition(@RequestParam long companyID, @RequestParam String positionName, @RequestParam int minSalary) {
        int numberOfUpdatedRows = employeeService.updateSalaryForPosition(companyID, positionName, minSalary);
        return String.format("%d employee(s) had their salary updated", numberOfUpdatedRows);
    }

    //Deleting an existing employee
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    //Listing employees with salary higher than a given value passed as query parameter
    /*
    @GetMapping("/filterBySalary")
    public List<EmployeeDTO> getEmployeesWithHighSalary(@RequestParam int salary) {
        List<EmployeeDTO> employeeList = new ArrayList<>(employees.values());
        return employeeList.stream().filter(e -> e.getSalary() > salary).toList();
    }
    */
}
