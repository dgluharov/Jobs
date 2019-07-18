package Jobs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Employer", schema = "dbo", catalog = "Jobs")
public class EmployerEntity extends Base {
    //    private int id;
//    private String name;
    private int ActiveJobsAdCounter;
    private List<JobsAdEntity> jobsAdEntityList = new ArrayList<>();


//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @Basic
//    @Column(name = "Name")
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    @Basic
    @Column(name = "ActiveJobsAdCounter")
    public int getActiveJobsAdCounter() {
        return ActiveJobsAdCounter;
    }

    public void setActiveJobsAdCounter(int jobsAdCounter) {
        this.ActiveJobsAdCounter = jobsAdCounter;
    }

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "EmployerId")
    public List<JobsAdEntity> getJobsAdEntityList() {
        return jobsAdEntityList;
    }

    public void setJobsAdEntityList(List<JobsAdEntity> jobsAdEntityList) {
        this.jobsAdEntityList = jobsAdEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployerEntity that = (EmployerEntity) o;
        return id == that.id &&
                ActiveJobsAdCounter == that.ActiveJobsAdCounter &&
                Objects.equals(jobsAdEntityList, that.jobsAdEntityList) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ActiveJobsAdCounter, jobsAdEntityList);
    }
}
