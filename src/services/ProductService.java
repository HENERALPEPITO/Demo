package services;

import models.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product addProduct(String name, BigDecimal price);
    boolean removeProduct(int id);
    Product findById(int id);
    boolean existsByName(String name);
    int nextId();
}
