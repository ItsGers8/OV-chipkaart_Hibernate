import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session currentSession;
    private Transaction currentTransaction;

    public AdresDAOHibernate() {}

    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public Session openSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openTransactionSession() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeSession() {
        currentSession.close();
    }

    public void closeTransactionSession() {
        currentTransaction.commit();
        currentSession.close();
    }

    @Override
    public void save(Adres adres) {
        getCurrentSession().save(adres);
    }

    @Override
    public void update(Adres adres) {
        getCurrentSession().update(adres);
    }

    @Override
    public void delete(Adres adres) {
        getCurrentSession().delete(adres);
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Criteria criteria = currentSession.createCriteria(Adres.class);
        return (Adres) criteria.add(Restrictions.eq("reiziger_id", reiziger.getId())).uniqueResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Adres> findAll() {
        return (List<Adres>) getCurrentSession().createQuery("from Adres").list();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
}
