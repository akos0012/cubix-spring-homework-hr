package hu.cubix.hr.akos0012.service.payRaiseService;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private EmployeePayRaiseService employeePayRaiseService;


    public SalaryService(EmployeePayRaiseService employeePayRaiseService) {
        this.employeePayRaiseService = employeePayRaiseService;
    }

    public void setNewSalary(Employee employee) {
        int currentSalary = employee.getSalary();
        int payRaisePercent = employeePayRaiseService.getPayRaisePercent(employee);
        int percent_divisor = 100;
        int newSalary = currentSalary + (currentSalary * payRaisePercent / percent_divisor);
        employee.setSalary(newSalary);
    }

    public int getPercentageOfPayRaise(Employee employee) {
        return employeePayRaiseService.getPayRaisePercent(employee);
    }
}
