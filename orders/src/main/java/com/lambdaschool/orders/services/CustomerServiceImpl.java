package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerservice")
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomersRepository customerrepo;

    @Override
    public List<Customer> findAllCustomerOrders() {
        List<Customer> list = new ArrayList<>();
        customerrepo.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customerrepo.save(customer);
    }

    @Override
    public Customer findCustomerByID(long customerID) {
        return customerrepo.findById(customerID)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customerID + " not found."));
    }

    @Override
    public List<Customer> findCustomerByKeyword(String keyword) {
        List<Customer> list = customerrepo.findByCustnameContainingIgnoringCase(keyword);
        return list;
    }
}
