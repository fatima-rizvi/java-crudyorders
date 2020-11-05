package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    //    http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{orderID}", produces = {"application/json"})
    public ResponseEntity<?> findOrderByID(@PathVariable long orderID) {
        Order o = orderService.findOrderByID(orderID);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    //POST /orders/order - adds a new order to an existing customer
    @PostMapping(value = "/order", consumes = {"application/json"})
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder) {
        //id are not recognized by POST
        newOrder.setOrdnum(0);
        newOrder = orderService.save(newOrder);

        //Set location headers for the new resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordnum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //PUT /orders/order/{ordernum} - completely replaces the given order record
    @PutMapping(value = "/order/{ordernum}", consumes = {"application/json"})
    public ResponseEntity<?> updateOrder(@Valid @RequestBody Order updateOrder, @PathVariable long ordernum) {
        updateOrder.setOrdnum(ordernum);
        orderService.save((updateOrder));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE /orders/order/{ordername} - deletes the given order

}
