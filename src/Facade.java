import Jobs.*;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Facade {
    private static final SessionFactory ourSessionFactory;
    private static Facade object;

    static {
        try {
            org.hibernate.cfg.Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HandlerException {
        return ourSessionFactory.openSession();
    }

    public static Facade getInstance() {
        if (object == null) {
            object = new Facade();
        }
        return object;
    }

    public void insertCategory(String name) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(name);
            session.save(categoryEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Adding a new category - successful.");
    }

    public void insertEmployer(String name) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            EmployerEntity employerEntity = new EmployerEntity();
            employerEntity.setName(name);
            employerEntity.setActiveJobsAdCounter(0);
            session.saveOrUpdate(employerEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Adding a new employer - successful.");
    }

    public void registerEmployee(String name) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setName(name);
            session.update(employeeEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void insertJobAd(EmployerEntity employerEntity,
                            CategoryEntity categoryEntity,
                            String description,
                            boolean isActive) {
        if (employerEntity.getActiveJobsAdCounter() < 10) {
            Transaction transaction = null;
            try (Session session = getSession()) {
                transaction = session.beginTransaction();
                JobsAdEntity jobsAdEntity = new JobsAdEntity();
                jobsAdEntity.setActive(isActive);
                jobsAdEntity.setEmployerEntity(employerEntity);
                jobsAdEntity.setCategoryEntity(categoryEntity);
                jobsAdEntity.setDescription(description);
                if (isActive) {
                    employerEntity.setActiveJobsAdCounter(employerEntity.getActiveJobsAdCounter() + 1);
                }
                session.save(jobsAdEntity);
                session.update(employerEntity);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            System.out.println("Adding a new JobAd - successful.");

        } else {
            System.out.println("You can't post more than 10 active job ads");
        }
    }

    public void applyForAJob(JobsAdEntity jobsAdEntity, String employeeName) {
        if (!jobsAdEntity.getEmployeeEntityList().contains(employeeName)) {
            registerEmployee(employeeName);
        }
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            jobsAdEntity.getEmployeeEntityList().add(getEmployee(employeeName));
            session.update(jobsAdEntity);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("You can't apply for an ad more than once!");
            if (transaction != null) {
                transaction.rollback();
            }
        }

        System.out.println("Successfully applied for a job");
    }

    public void ActiveAdsWithApplyingEmployees() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ActiveAdsWithApplyingEmployees> query = builder.createQuery
                    (ActiveAdsWithApplyingEmployees.class);
            Root<ActiveAdsWithApplyingEmployees> root = query.from(ActiveAdsWithApplyingEmployees.class);
            query.select(root);
            Query<ActiveAdsWithApplyingEmployees> q = session.createQuery(query);
            List<ActiveAdsWithApplyingEmployees> activeAdsWithApplyingEmployeesList = q.getResultList();
            for (ActiveAdsWithApplyingEmployees activeAdsWithApplyingEmployees : activeAdsWithApplyingEmployeesList) {
                System.out.printf("Category: %10s | Active JobsAd: %2d | Applying People: %d \n",
                        activeAdsWithApplyingEmployees.getCategory(),
                        activeAdsWithApplyingEmployees.getActiveJob(),
                        activeAdsWithApplyingEmployees.getApplyingPeople());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<EmployerEntity> getEmployersEntity() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmployerEntity> query = builder.createQuery(EmployerEntity.class);
            Root<EmployerEntity> root = query.from(EmployerEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public EmployerEntity getEmployerEntity(String name) {
        for (EmployerEntity employerEntity : getEmployersEntity()) {
            if (employerEntity.getName().equals(name)) {
                return employerEntity;
            }
        }
        return null;
    }

    public List<CategoryEntity> getCategory() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);
            Root<CategoryEntity> root = query.from(CategoryEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public CategoryEntity getCategory(String name) {
        for (CategoryEntity categoryEntity : getCategory()) {
            if (categoryEntity.getName().equals(name)) {
                return categoryEntity;
            }
        }
        return null;
    }

    public List<JobsAdEntity> getJobAds() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<JobsAdEntity> query = builder.createQuery(JobsAdEntity.class);
            Root<JobsAdEntity> root = query.from(JobsAdEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public JobsAdEntity getJobAd(int id) {
        for (JobsAdEntity jobsAdEntity : getJobAds()) {
            if (jobsAdEntity.getId() == id) {
                return jobsAdEntity;
            }
        }
        return null;
    }

    public List<EmployeeEntity> getEmployees() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeEntity> query = builder.createQuery(EmployeeEntity.class);
            Root<EmployeeEntity> root = query.from(EmployeeEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public EmployeeEntity getEmployee(String name) {
        for (EmployeeEntity employeeEntity : getEmployees()) {
            if (employeeEntity.getName().equals(name)) {
                return employeeEntity;
            }
        }
        return null;
    }
}
