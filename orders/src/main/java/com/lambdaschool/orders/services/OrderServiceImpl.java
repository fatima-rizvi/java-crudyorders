package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderservice")
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrdersRepository orderrepo;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderrepo.save(order);
    }

    @Override
    public Order findOrderByID(long orderID) {
        return orderrepo.findById(orderID)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderID + " not found."));
    }
}
