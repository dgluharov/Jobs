package Jobs;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Records", schema = "dbo", catalog = "Jobs")
@IdClass(RecordsEntityPK.class)
public class RecordsEntity {
    private int employeeId;
    private int jobsAdId;

    @Id
    @Column(name = "EmployeeId")
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Id
    @Column(name = "JobsAdId")
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
        RecordsEntity that = (RecordsEntity) o;
        return employeeId == that.employeeId &&
                jobsAdId == that.jobsAdId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, jobsAdId);
    }
}
