package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService implements EmployeeService {
    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}
