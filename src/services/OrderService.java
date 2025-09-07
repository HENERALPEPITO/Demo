package services;

import models.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAll();
    void add(Order o);
    Order findByReference(String ref);
}
