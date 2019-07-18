package Jobs;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Employee", schema = "dbo", catalog = "Jobs")
public class EmployeeEntity extends Base {
    //    private int id;
//    private String name;
    private List<JobsAdEntity> jobsAdEntityList = new ArrayList<>();
//
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
//

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "employeeEntityList")
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
        EmployeeEntity that = (EmployeeEntity) o;
        return id == that.id &&
                Objects.equals(jobsAdEntityList, that.jobsAdEntityList) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, jobsAdEntityList);
    }

}
