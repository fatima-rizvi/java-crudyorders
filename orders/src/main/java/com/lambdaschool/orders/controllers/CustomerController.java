package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    //    http://localhost:2019/customers/orders (returns all customers with their orders)
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomerOrders() {
        List<Customer> rtnList = customerService.findAllCustomerOrders();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


//    http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{customerID}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByID(@PathVariable long customerID) {
        Customer c = customerService.findCustomerByID(customerID);
        return new ResponseEntity<>(c, HttpStatus.OK);
}

//    http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{keyword}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByKeyword(@PathVariable String keyword) {
        List<Customer> rtnList = customerService.findCustomerByKeyword(keyword);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


}
