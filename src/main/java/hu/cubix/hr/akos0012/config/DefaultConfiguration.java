package hu.cubix.hr.akos0012.config;

import hu.cubix.hr.akos0012.service.payRaiseService.DefaultEmployeePayRaiseService;
import hu.cubix.hr.akos0012.service.payRaiseService.EmployeePayRaiseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {

    @Bean
    public EmployeePayRaiseService employeePayRaiseService() {
        return new DefaultEmployeePayRaiseService();
    }
}
