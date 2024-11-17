package hu.cubix.hr.akos0012.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
            private float limit1;
            private float limit2;
            private float limit3;
            private int raise1;
            private int raise2;
            private int raise3;

            public float getLimit1() {
                return limit1;
            }

            public void setLimit1(float limit1) {
                this.limit1 = limit1;
            }

            public float getLimit2() {
                return limit2;
            }

            public void setLimit2(float limit2) {
                this.limit2 = limit2;
            }

            public float getLimit3() {
                return limit3;
            }

            public void setLimit3(float limit3) {
                this.limit3 = limit3;
            }

            public int getRaise1() {
                return raise1;
            }

            public void setRaise1(int raise1) {
                this.raise1 = raise1;
            }

            public int getRaise2() {
                return raise2;
            }

            public void setRaise2(int raise2) {
                this.raise2 = raise2;
            }

            public int getRaise3() {
                return raise3;
            }

            public void setRaise3(int raise3) {
                this.raise3 = raise3;
            }
        }
    }
}
