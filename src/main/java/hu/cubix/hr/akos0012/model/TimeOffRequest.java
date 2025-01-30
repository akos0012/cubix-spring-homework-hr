package hu.cubix.hr.akos0012.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TimeOffRequest {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private final Employee employee;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private Employee manager;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public TimeOffRequest() {
        this.employee = null;
        this.createdAt = null;
    }

    public TimeOffRequest(Employee employee, LocalDateTime startDate, LocalDateTime endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestStatus = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void judge(RequestStatus status, Employee manager) {
        setManager(manager);
        setRequestStatus(status);
        setUpdatedAt(LocalDateTime.now());
    }

    public long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeOffRequest request)) return false;
        return id == request.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeOffRequest{" +
                "id=" + id +
                ", employee=" + employee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", manager=" + manager +
                ", requestStatus=" + requestStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
