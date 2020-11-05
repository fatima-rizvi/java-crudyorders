package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

//    POST /customers/customer - Adds a new customer including any new orders
    @PostMapping(value = "/customer", consumes = {"application/json"})
    public ResponseEntity<?> addNewCustomer( @Valid @RequestBody Customer newCustomer) {
        //We don't need to give it an id, it will automatically generate it
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);

        //Set the location header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

//    PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "/customer/{custcode}", consumes = {"application/json"})
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode) {
        updateCustomer.setCustcode(custcode);
        customerService.save(updateCustomer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    @PatchMapping(value = "customer/{custcode}", consumes = {"application/json"})
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode) {
        customerService.update(updateCustomer, custcode);

        return  new ResponseEntity<>(HttpStatus.OK);
    }

//    DELETE /customers/customer/{custcode} - Deletes the given customer including any associated orders
    @DeleteMapping(value = "customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode) {
        customerService.delete(custcode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
