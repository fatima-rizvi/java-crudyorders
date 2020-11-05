package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderservice")
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrdersRepository orderrepo;

    @Autowired
    CustomersRepository customerrepo;

    @Autowired
    PaymentRepository paymentrepo;

    @Transactional
    @Override
    public Order save(Order order) {

        Order newOrder = new Order();

        if (order.getOrdnum() != 0) {
            orderrepo.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        //Verify single fields
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.setCustomer(customerrepo.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getCustomer().getCustcode() + " not found")));

        //Verify collections
        // Payments
        newOrder.getPayments()
                .clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = paymentrepo.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found"));

            newOrder.getPayments().add(newPayment);
        }

        return orderrepo.save(order);

    }

    @Transactional
    @Override
    public Order update(Order order, long id) {

        Order currentOrder = orderrepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found"));

        currentOrder.setOrdnum(order.getOrdnum());

        //Verify single fields
        if (order.hasOrdamount) currentOrder.setOrdamount(order.getOrdamount());
        if (order.hasAdvanceamount) currentOrder.setAdvanceamount(order.getAdvanceamount());
        if (order.getOrderdescription() != null) currentOrder.setOrderdescription(order.getOrderdescription());
        if (order.getCustomer() != null) currentOrder.setCustomer(order.getCustomer());

        //Verify collections
        // Payments

        if (order.getPayments().size() > 0) {
            currentOrder.getPayments()
                    .clear();
            for (Payment p : order.getPayments()) {
                Payment newPayment = paymentrepo.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found"));

                currentOrder.getPayments().add(newPayment);
            }
        }

        return orderrepo.save(order);

    }

    @Override
    public Order findOrderByID(long orderID) {
        return orderrepo.findById(orderID)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderID + " not found."));
    }
}
