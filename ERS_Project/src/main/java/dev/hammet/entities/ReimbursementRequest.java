package dev.hammet.entities;

public class ReimbursementRequest {

    // attributes: a request id, an associated employee id, amount, description, and an enum "status"
    public enum Status {
        PENDING,
        APPROVED,
        DENIED
    };
    private Status status;
    private String description;
    private float amount;

    private int id;
    private int employeeId;

    public ReimbursementRequest() {

    }

    public ReimbursementRequest(int id, int employeeId, float amount, String description) {

        this.id = id;
        this.employeeId = employeeId;

        this.amount = amount;
        this.description = description;

        this.status = Status.PENDING;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "status=" + status +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                ", employeeId=" + employeeId +
                '}';
    }
}
