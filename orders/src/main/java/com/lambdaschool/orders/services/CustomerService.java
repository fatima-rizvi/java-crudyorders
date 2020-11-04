package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomerOrders();

    Customer save(Customer customer);

    Customer findCustomerByID(long customerID);

    List<Customer> findCustomerByKeyword(String keyword);

}
