package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomerOrders();

    Customer save(Customer customer); //POST/PATCH

    Customer findCustomerByID(long customerID);

    List<Customer> findCustomerByKeyword(String keyword);

    //Update method
    Customer update(Customer customer, long id);

    //Delete method
    void delete(long custcode);

    //Delete all method, not available to all users. only to be used in seed data
    void deleteAllCustomers();
}
