package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import hu.cubix.hr.akos0012.mapper.EmployeeMapper;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.service.EmployeeService;
import hu.cubix.hr.akos0012.service.payRaiseService.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    /*@RequestParam(required =false) Integer salary*/
    public List<EmployeeDTO> findAll(@RequestParam Optional<Integer> salary) {
        List<Employee> employeeList = employeeService.findAll();
        if (salary.isEmpty()) return employeeMapper.employeesToDtos(employeeList);

        List<Employee> filteredEmployees = employeeList.stream().filter(e -> e.getSalary() > salary.get()).toList();
        return employeeMapper.employeesToDtos(filteredEmployees);
    }

    //Return an employee with a given id
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

    //Modifying an existing employee
    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable long id, @RequestBody @Valid EmployeeDTO employeeDTO) {

        if (employeeDTO.id() != id) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
        Employee updatedEmployee = employeeService.update(employee);


        if (updatedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(updatedEmployee);
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
