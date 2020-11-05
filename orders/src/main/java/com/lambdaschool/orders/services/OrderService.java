package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

public interface OrderService {
    Order save(Order order); //POST method

    Order update(Order order, long id); //PUT method

    Order findOrderByID(long orderID);
}
