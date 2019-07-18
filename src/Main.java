import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
//        final Session session = getSession();
//        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        } finally {
//            session.close();
//        }


//        Facade facade = new Facade();
//        facade.insertEmployer("Nexo");
//        facade.insertJobAd(facade.getEmployerEntity("Nexo"),
//                facade.getCategory("Manager"), "Supervise the team", true);
//        facade.insertJobAd(facade.getEmployerEntity("Musala Soft"), facade.getCategory("QA"),
//                "Quality Control", true);
//        facade.applyForAJob(facade.getJobAd(12), "Petar Markov");
//        facade.applyForAJob(facade.getJobAd(12), "Boris Borisov");
//        facade.applyForAJob(facade.getJobAd(12), "Boris Borisov");
//        facade.ActiveAdsWithApplyingEmployees();
    }
}