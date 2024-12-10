package hu.cubix.hr.akos0012.service.payRaiseService;

import hu.cubix.hr.akos0012.config.HrConfigurationProperties;
import hu.cubix.hr.akos0012.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.cubix.hr.akos0012.config.HrConfigurationProperties.Smart;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class SmartEmployeePayRaiseService implements EmployeePayRaiseService {

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

//        List<Float> limits = smartConfig.getLimits();
//        List<Integer> raises = smartConfig.getRaises();
//
//        for (int i = 0; i < limits.size(); i++) {
//            if (yearsAtJob >= limits.get(i)) return raises.get(i);
//        }


        TreeMap<Double, Integer> limits = smartConfig.getLimits();

        //1. megoldás
//        Integer maxPercent = null;
//        for (Entry<Double, Integer> entry : limits.entrySet()) {
//            if (yearsAtJob >= entry.getKey())
//                maxPercent = entry.getValue();
//            else
//                break;
//        }
//        return maxPercent == null ? 0 : maxPercent;

        //2. megoldás

        Optional<Double> optionalMax = limits.keySet().stream().filter(k -> k <= yearsAtJob).max(Double::compare);
        return optionalMax.isEmpty() ? 0 : limits.get(optionalMax.get());

        //3. megoldás
//        Entry<Double, Integer> floorEntry = limits.floorEntry(yearsAtJob);
//        return floorEntry == null ? 0 : floorEntry.getValue();
    }

    private double getYearsAtJob(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        LocalDate jobStartDate = employee.getDateOfStartWork().toLocalDate();
        return Period.between(jobStartDate, currentDate).toTotalMonths() / 12.0;
    }
}
