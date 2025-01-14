package hu.cubix.hr.akos0012.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//public enum CompanyForm {
//    LIMITED_PARTNERSHIP,
//    LLC,
//    CORPORATION
//}

@Entity
public class CompanyForm {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    public CompanyForm() {
    }

    public CompanyForm(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}