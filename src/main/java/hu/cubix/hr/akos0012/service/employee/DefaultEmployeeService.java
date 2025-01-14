package hu.cubix.hr.akos0012.service.employee;

import hu.cubix.hr.akos0012.config.HrConfigurationProperties;
import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService extends AbstractEmployeeService {
    @Autowired
    private HrConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        return config.getSalary().getDef().getPercent();
    }
}
