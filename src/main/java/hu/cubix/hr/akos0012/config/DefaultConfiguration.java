package hu.cubix.hr.akos0012.config;

import hu.cubix.hr.akos0012.service.DefaultEmployeeService;
import hu.cubix.hr.akos0012.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
public class DefaultConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new DefaultEmployeeService();
    }
}
