package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

public interface OrderService {
    Order save(Order order);

    Order findOrderByID(long orderID);
}
