import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        testHibernate();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testHibernate() {
        Reiziger reiziger = new Reiziger(6, "G.J.", "", "Mak",
                LocalDate.of(2000, 10, 8));
        Adres adres = new Adres(6, "3451XK", "12", "Secr. Verstgln.",
                "Vleuten", reiziger);
        OVChipkaart ovChipkaart = new OVChipkaart(83956,
                Date.valueOf(LocalDate.of(2022, 10, 12)), 2, 2.5, reiziger);
        Product product = new Product(7, "Seniorenkorting",
                "Goedkoper reizen omdat je van de tijd die je nog hebt het beste moet maken.", 12.5);

        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();
        AdresDAOHibernate adao = new AdresDAOHibernate();
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate();
        ProductDAOHibernate pdao = new ProductDAOHibernate();

        reset(rdao, reiziger, adao, adres, odao, ovChipkaart, pdao, product);

        ovChipkaart.addToProducten(product); // Koppel het product aan de OV Kaart
        product.addToOVKaarten(ovChipkaart); // Koppel de OV Kaart aan het product

        System.out.println("Alle reizigers:");
        rdao.openSession();
        rdao.findAll().forEach(System.out::println);
        rdao.closeSession();

        System.out.println("\nNieuwe reiziger toevoegen...");
        rdao.openTransactionSession();
        rdao.save(reiziger);
        rdao.closeTransactionSession();

        System.out.println("Alle reizigers met nieuwe reiziger:");
        rdao.openSession();
        rdao.findAll().forEach(System.out::println);
        rdao.closeSession();

        System.out.println("\nAlle adressen:");
        adao.openSession();
        adao.findAll().forEach(System.out::println);
        adao.closeSession();

        System.out.println("\nNieuw adres toevoegen...");
        adao.openTransactionSession();
        adao.save(adres);
        adao.closeTransactionSession();

        System.out.println("\nAlle adressen met nieuw Adres:");
        adao.openSession();
        adao.findAll().forEach(System.out::println);
        adao.closeSession();

        System.out.println("\nAlle OV kaarten:");
        odao.openSession();
        odao.findAll().forEach(System.out::println);
        odao.closeSession();

        System.out.println("\nNieuwe OV kaart toevoegen...");
        odao.openTransactionSession();
        odao.save(ovChipkaart);
        odao.closeTransactionSession();

        System.out.println("\nAlle OV kaarten met nieuwe OV kaart:");
        odao.openSession();
        odao.findAll().forEach(System.out::println);
        odao.closeSession();

        System.out.println("\nAlle producten bevat nu ook meteen het gekoppelde product:");
        pdao.openSession();
        pdao.findAll().forEach(System.out::println);
        pdao.closeSession();
    }

    private static void reset(ReizigerDAOHibernate rdao, Reiziger reiziger,
                              AdresDAOHibernate adao, Adres adres,
                              OVChipkaartDAOHibernate odao, OVChipkaart ovChipkaart,
                              ProductDAOHibernate pdao, Product product) {
        adao.openTransactionSession();
        adao.delete(adres);
        adao.closeTransactionSession();

        odao.openTransactionSession();
        odao.delete(ovChipkaart);
        odao.closeTransactionSession();

        pdao.openTransactionSession();
        pdao.delete(product);
        pdao.closeSession();

        rdao.openTransactionSession();
        rdao.delete(reiziger);
        rdao.closeTransactionSession();
    }
}