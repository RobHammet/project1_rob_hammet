package dev.hammet.entities;

public class ReimbursementRequest {

    // attributes: a request id, an associated employee id, amount, description, and an enum "status"
    public enum Status {
        PENDING,
        APPROVED,
        DENIED
    };
    public enum Type {
        TRAVEL,
        LODGING,
        FOOD,
        OTHER
    }
    private Status status;

    private Type type;
    private String description;
    private float amount;

    private int id;
    private int employeeId;

    public ReimbursementRequest() {
        this.status = Status.PENDING;
        this.type = Type.OTHER;
    }

    public ReimbursementRequest(int id, int employeeId, float amount, String description, Type type) {

        this.id = id;
        this.employeeId = employeeId;

        this.amount = amount;
        this.description = description;

        this.status = Status.PENDING;
        this.type = Type.OTHER;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "status=" + status.name() +
                ", type=" + type.name() +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                ", employeeId=" + employeeId +
                '}';
    }
}
