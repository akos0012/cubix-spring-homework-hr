package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTFController {

    public List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(516151, "OTP Bank", 10000, LocalDateTime.of(2020, 2, 14, 8, 0)));
        employees.add(new Employee(425893, "OTP Bank", 10000, LocalDateTime.of(2010, 5, 4, 8, 0)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "index";
    }

    @PostMapping("/employee")
    public String createEmployee(Employee employee) {
        employees.add(employee);
        return "redirect:/";
    }

}
