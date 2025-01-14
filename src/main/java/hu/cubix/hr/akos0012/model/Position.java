package hu.cubix.hr.akos0012.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Position {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Qualification qualification;
    //private int minSalary;

    public Position() {
    }

    public Position(String name, Qualification qualification) {
        this.name = name;
        this.qualification = qualification;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification=" + qualification +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return id == position.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
