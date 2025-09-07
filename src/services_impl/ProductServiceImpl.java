package services_impl;

import models.Product;
import services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product addProduct(String name, BigDecimal price) {
        Product p = new Product(nextId(), name, price);
        products.add(p);
        return p;
    }

    @Override
    public boolean removeProduct(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    @Override
    public Product findById(int id) {
        for (Product p : products) if (p.getId() == id) return p;
        return null;
    }

    @Override
    public boolean existsByName(String name) {
        if (name == null) return false;
        for (Product p : products) if (p.getName().equalsIgnoreCase(name)) return true;
        return false;
    }

    @Override
    public int nextId() {
        int max = 0;
        for (Product p : products) if (p.getId() > max) max = p.getId();
        return max + 1;
    }
}
