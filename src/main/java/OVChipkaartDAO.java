import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
    void save(OVChipkaart ovChipkaart);
    void update(OVChipkaart ovChipkaart);
    void delete(OVChipkaart ovChipkaart);
    OVChipkaart findByKaartnummer(int kaart_nummer);
    ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findAll();
}
