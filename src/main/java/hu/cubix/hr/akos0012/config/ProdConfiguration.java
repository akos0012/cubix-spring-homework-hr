package hu.cubix.hr.akos0012.config;

import hu.cubix.hr.akos0012.service.EmployeeService;
import hu.cubix.hr.akos0012.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class ProdConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }
}
