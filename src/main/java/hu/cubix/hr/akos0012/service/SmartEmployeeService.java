package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.config.HrConfigurationProperties;
import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import hu.cubix.hr.akos0012.config.HrConfigurationProperties.Salary.Smart;

import java.time.LocalDate;
import java.time.Period;

@Service
public class SmartEmployeeService implements EmployeeService {

//    @Value("${hr.salary.smart.limit1}")
//    private float limit1;
//    @Value("${hr.salary.smart.limit2}")
//    private float limit2;
//    @Value("${hr.salary.smart.limit3}")
//    private float limit3;
//    @Value("${hr.salary.smart.raise1}")
//    private int raise1;
//    @Value("${hr.salary.smart.raise2}")
//    private int raise2;
//    @Value("${hr.salary.smart.raise3}")
//    private int raise3;

    @Autowired
    private HrConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {

        Smart smartConfig = config.getSalary().getSmart();
        double yearsAtJob = getYearsAtJob(employee);

        float limit1 = smartConfig.getLimit1();
        float limit2 = smartConfig.getLimit2();
        float limit3 = smartConfig.getLimit3();

        int raise1 = smartConfig.getRaise1();
        int raise2 = smartConfig.getRaise2();
        int raise3 = smartConfig.getRaise3();

        if (yearsAtJob >= limit1) return raise1;
        if (yearsAtJob >= limit2) return raise2;
        if (yearsAtJob >= limit3) return raise3;
        return 0;
    }

    private double getYearsAtJob(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        LocalDate jobStartDate = employee.getTimestamp().toLocalDate();
        return Period.between(jobStartDate, currentDate).toTotalMonths() / 12.0;
    }
}
