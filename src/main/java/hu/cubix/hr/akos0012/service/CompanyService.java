package hu.cubix.hr.akos0012.service;

import hu.cubix.hr.akos0012.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    private final Map<Long, Company> companies = new HashMap<>();

    public List<Company> findAll() {
        return new ArrayList<>(companies.values());
    }

    public Company findById(long id) {
        return companies.get(id);
    }

    private Company save(Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    public Company create(Company company) {
        if (findById(company.getId()) != null) return null;
        return save(company);
    }

    public Company update(Company company) {
        if (findById(company.getId()) == null) return null;
        return save(company);
    }

    public void delete(long id) {
        companies.remove(id);
    }
}
