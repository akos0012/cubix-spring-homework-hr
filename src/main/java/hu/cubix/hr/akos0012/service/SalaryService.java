package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private EmployeeService employeeService;


    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setNewSalary(Employee employee) {
        int currentSalary = employee.getSalary();
        int payRaisePercent = employeeService.getPayRaisePercent(employee);
        int percent_divisor = 100;
        int newSalary = currentSalary + (currentSalary * payRaisePercent / percent_divisor);
        employee.setSalary(newSalary);
    }

    public int getPercentageOfPayRaise(Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
}
