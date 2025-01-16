package hu.cubix.hr.akos0012.service.employee.specification;

import hu.cubix.hr.akos0012.model.Company_;
import hu.cubix.hr.akos0012.model.Employee;
import hu.cubix.hr.akos0012.model.Employee_;
import hu.cubix.hr.akos0012.model.Position_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecifications {
    public static Specification<Employee> hasID(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> employeeNameStartWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), prefix.toLowerCase() + '%');
    }

    public static Specification<Employee> hasEmployeePositionName(String position) {
        return (root, cq, cb) -> cb.equal(cb.lower(root.get(Employee_.position).get(Position_.name)), position.toLowerCase());
    }

    public static Specification<Employee> hasSalaryAround(int salary) {
        int lowerBound = (int) (salary * 0.95);
        int upperBound = (int) (salary * 1.05);

        return (root, cq, cb) -> cb.between(root.get(Employee_.salary), lowerBound, upperBound);
    }

    public static Specification<Employee> hasEntryDate(LocalDate entryDate) {
        return (root, cq, cb) -> cb.equal(cb.function("DATE", LocalDate.class, root.get(Employee_.dateOfStartWork)), entryDate);
    }

    public static Specification<Employee> companyNameStartWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), prefix.toLowerCase() + '%');
    }
}
