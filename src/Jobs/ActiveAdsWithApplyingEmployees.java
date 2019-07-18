package Jobs;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "ActiveAdsWithApplyingEmployees", schema = "dbo", catalog = "Jobs")
@Immutable
public class ActiveAdsWithApplyingEmployees {
    private int id;
    private String category;
    private int activeJob;
    private int applyingPeople;


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
    @Column(name = "Category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "ActiveJobsAd")
    public int getActiveJob() {
        return activeJob;
    }

    public void setActiveJob(int activeJob) {
        this.activeJob = activeJob;
    }

    @Basic
    @Column(name = "ApplyingPeople")
    public int getApplyingPeople() {
        return applyingPeople;
    }

    public void setApplyingPeople(int applyingPeople) {
        this.applyingPeople = applyingPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveAdsWithApplyingEmployees that = (ActiveAdsWithApplyingEmployees) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(activeJob, that.activeJob) &&
                Objects.equals(applyingPeople, that.applyingPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, activeJob, applyingPeople);
    }
}
