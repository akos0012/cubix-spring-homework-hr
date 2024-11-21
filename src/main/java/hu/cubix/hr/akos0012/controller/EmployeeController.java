package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.dto.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final Map<Long, EmployeeDTO> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDTO(1, "OTP Bank", 12000, LocalDateTime.of(2020, 2, 14, 8, 0)));
        employees.put(2L, new EmployeeDTO(2, "OTP Bank", 10000, LocalDateTime.of(2010, 5, 4, 8, 0)));
    }

    //Listing all employees
    //Listing employees with salary higher than a given value passed as query parameter
    @GetMapping
    public List<EmployeeDTO> findAll(@RequestParam(required = false) Integer salary) {
        List<EmployeeDTO> employeeList = new ArrayList<>(employees.values());
        if (salary != null)
            return employeeList.stream().filter(e -> e.getSalary() > salary).toList();
        return employeeList;
    }

    //Return an employee with a given id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable long id) {
        EmployeeDTO employee = employees.get(id);
        if (employee != null) return ResponseEntity.ok(employee);
        return ResponseEntity.notFound().build();
    }

    //Adding a new employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employee) {
        if (employees.containsKey(employee.getId())) return ResponseEntity.badRequest().build();
        employees.put(employee.getId(), employee);
        return ResponseEntity.ok(employee);
    }

    //Modifying an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable long id, @RequestBody EmployeeDTO employee) {
        if (!employees.containsKey(id)) return ResponseEntity.notFound().build();
        if (employee.getId() != id) return ResponseEntity.badRequest().build();

        employees.put(id, employee);
        return ResponseEntity.ok(employee);
    }

    //Deleting an existing employee
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employees.remove(id);
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
