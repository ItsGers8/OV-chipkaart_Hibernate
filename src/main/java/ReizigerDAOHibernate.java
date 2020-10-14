import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session currentSession;
    private Transaction currentTransaction;

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
    public void save(Reiziger reiziger) {
        getCurrentSession().save(reiziger);
    }

    @Override
    public void update(Reiziger reiziger) {
        getCurrentSession().update(reiziger);
    }

    @Override
    public void delete(Reiziger reiziger) {
        getCurrentSession().delete(reiziger);
    }

    @Override
    public Reiziger findById(int id) {
        return getCurrentSession().get(Reiziger.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Reiziger> findByGbdatum(String datum) {
        Criteria criteria = currentSession.createCriteria(Adres.class);
        return (ArrayList<Reiziger>) criteria.add(Restrictions.eq("geboortedatum", datum)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Reiziger> findAll() {
        return (ArrayList<Reiziger>) getCurrentSession().createQuery("from Reiziger").list();
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
