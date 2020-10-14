import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private Session currentSession;
    private Transaction currentTransaction;

    public ProductDAOHibernate() {}

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
    public void save(Product product) {
        getCurrentSession().save(product);
    }

    @Override
    public void update(Product product) {
        getCurrentSession().update(product);
    }

    @Override
    public void delete(Product product) {
        getCurrentSession().delete(product);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findAll() {
        return (List<Product>) getCurrentSession().createQuery("from Product").list();
    }
}
