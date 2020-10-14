import java.util.List;

public interface ProductDAO {
    void save(Product product);
    void update(Product product);
    void delete(Product product);
    List<Product> findAll();
}
