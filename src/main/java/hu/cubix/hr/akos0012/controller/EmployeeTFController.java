package hu.cubix.hr.akos0012.controller;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class EmployeeTFController {

    public List<Employee> employees = new ArrayList<>();

    {
        //employees.add(new Employee("Akos", "Developer", 10000, LocalDateTime.of(2020, 2, 14, 8, 0)));
        //employees.add(new Employee("Barna", "Tester", 10000, LocalDateTime.of(2010, 5, 4, 8, 0)));
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

    @GetMapping("/editEmployee/{id}")
    public String showEditEmployeePage(@PathVariable long id, Model model) {
        Optional<Employee> employee = employees.stream().filter(e -> e.getId() == id).findAny();
        if (employee.isEmpty()) return "redirect:/";
        model.addAttribute("employee", employee.get());
        return "edit-employee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(Employee updatedEmployee) {
        employees = employees.stream()
                .map(e -> e.getId() == updatedEmployee.getId() ? updatedEmployee : e)
                .collect(Collectors.toList());

        return "redirect:/";
    }

    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employees.removeIf(e -> e.getId() == id);
        return "redirect:/";
    }

}
