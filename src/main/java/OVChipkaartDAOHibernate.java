import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session currentSession;
    private Transaction currentTransaction;

    public OVChipkaartDAOHibernate() {}

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
    public void save(OVChipkaart ovChipkaart) {
        getCurrentSession().save(ovChipkaart);
    }

    @Override
    public void update(OVChipkaart ovChipkaart) {
        getCurrentSession().update(ovChipkaart);
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) {
        getCurrentSession().delete(ovChipkaart);
    }

    @Override
    public OVChipkaart findByKaartnummer(int kaart_nummer) {
        return getCurrentSession().get(OVChipkaart.class, kaart_nummer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Criteria criteria = currentSession.createCriteria(OVChipkaart.class);
        return (ArrayList<OVChipkaart>) criteria.add(Restrictions.eq("reiziger_id", reiziger.getId())).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OVChipkaart> findAll() {
        return (List<OVChipkaart>) getCurrentSession().createQuery("from OVChipkaart").list();
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
