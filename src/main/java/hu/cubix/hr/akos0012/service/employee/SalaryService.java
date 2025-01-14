package hu.cubix.hr.akos0012.service.employee;

import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.repository.EmployeeRepository;
import hu.cubix.hr.akos0012.repository.PositionDetailsByCompanyRepository;
import hu.cubix.hr.akos0012.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    private final EmployeeService employeeService;
    private PositionRepository positionRepository;
    private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
    private EmployeeRepository employeeRepository;

    public SalaryService(EmployeeService employeeService, PositionRepository positionRepository, PositionDetailsByCompanyRepository positionDetailsByCompanyRepository, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
        this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
        this.employeeRepository = employeeRepository;
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

    @Transactional
    public void raiseMinSalary(long companyID, String positionName, int minSalary) {
        positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyID)
                .forEach(pd -> {
                    pd.setMinSalary(minSalary);
                    employeeRepository.updateSalaryForPosition(companyID, positionName, minSalary);
                });
    }
}
