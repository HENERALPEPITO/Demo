package services_impl;

import models.Order;
import services.OrderService;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getAll() {
        return orders;
    }

    @Override
    public void add(Order o) {
        orders.add(o);
    }

    @Override
    public Order findByReference(String ref) {
        for (Order o : orders) if (o.getReference().equalsIgnoreCase(ref)) return o;
        return null;
    }
}
