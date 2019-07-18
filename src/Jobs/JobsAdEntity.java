package Jobs;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "JobsAd", schema = "dbo", catalog = "Jobs")
public class JobsAdEntity {
    private int id;
    private String description;
    private boolean isActive;
    private EmployerEntity employerEntity;
    private CategoryEntity categoryEntity;
    private List<EmployeeEntity> employeeEntityList = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "IsActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "EmployerId")
    public EmployerEntity getEmployerEntity() {
        return employerEntity;
    }

    public void setEmployerEntity(EmployerEntity employerEntity) {
        this.employerEntity = employerEntity;
    }


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "CategoryId")
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Records",
            joinColumns = {
                    @JoinColumn(name = "JobsAdId", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EmployeeId", nullable = false, updatable = false)})
    public List<EmployeeEntity> getEmployeeEntityList() {
        return employeeEntityList;
    }

    public void setEmployeeEntityList(List<EmployeeEntity> employeeEntityList) {
        this.employeeEntityList = employeeEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobsAdEntity that = (JobsAdEntity) o;
        return id == that.id &&
                isActive == that.isActive &&
                Objects.equals(employerEntity, that.employerEntity) &&
                Objects.equals(categoryEntity, that.categoryEntity) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, isActive, employerEntity, categoryEntity);
    }
}
