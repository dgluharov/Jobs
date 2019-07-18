package Jobs;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RecordsEntityPK implements Serializable {
    private int employeeId;
    private int jobsAdId;

    @Column(name = "EmployeeId")
    @Id
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Column(name = "JobsAdId")
    @Id
    public int getJobsAdId() {
        return jobsAdId;
    }

    public void setJobsAdId(int jobsAdId) {
        this.jobsAdId = jobsAdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordsEntityPK that = (RecordsEntityPK) o;
        return employeeId == that.employeeId &&
                jobsAdId == that.jobsAdId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, jobsAdId);
    }
}
