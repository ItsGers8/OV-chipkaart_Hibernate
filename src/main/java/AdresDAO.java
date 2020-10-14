import java.util.List;

public interface AdresDAO {
    void save(Adres adres);
    void update(Adres adres);
    void delete(Adres adres);
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll();
}
