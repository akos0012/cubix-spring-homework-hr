package hu.cubix.hr.akos0012.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigurationProperties {
    private Salary salary;

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public static class Salary {
        private Smart smart;

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }

        public static class Smart {
            private List<Float> limits;
            private List<Integer> raises;

            public List<Float> getLimits() {
                return limits;
            }

            public void setLimits(List<Float> limits) {
                this.limits = limits;
            }

            public List<Integer> getRaises() {
                return raises;
            }

            public void setRaises(List<Integer> raises) {
                this.raises = raises;
            }
        }
    }
}
