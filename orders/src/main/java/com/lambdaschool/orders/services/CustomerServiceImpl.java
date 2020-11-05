package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
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

    @Autowired
    OrdersRepository orderrepo;

    @Autowired
    AgentsRepository agentrepo;

    @Autowired
    PaymentRepository paymentrepo;

    @Override
    public List<Customer> findAllCustomerOrders() {
        List<Customer> list = new ArrayList<>();
        customerrepo.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Transactional
    @Override
    //Post method
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            customerrepo.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        //Verify single fields
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        newCustomer.setAgent(agentrepo.findById(customer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found")));

        //Verify collections
        //Orders
        newCustomer.getOrders()
                .clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = orderrepo.findById(o.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " not found"));

            newCustomer.getOrders().add(newOrder);

            //Payments
            for(Payment p : o.getPayments()) {
                Payment newPayment = paymentrepo.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found"));

                newOrder.getPayments().add(newPayment);
            }
        }

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

    @Transactional
    @Override
    //Put/patch method
    public Customer update(Customer customer, long id) {
        Customer currentCustomer = customerrepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found"));

        //Verify single fields
        if (customer.getCustname() != null) currentCustomer.setCustname(customer.getCustname());
        if (customer.getCustcity() != null) currentCustomer.setCustcity(customer.getCustcity());
        if (customer.getWorkingarea() != null) currentCustomer.setWorkingarea(customer.getWorkingarea());
        if (customer.getCustcountry() != null) currentCustomer.setCustcountry(customer.getCustcountry());
        if (customer.getGrade() != null) currentCustomer.setGrade(customer.getGrade());
        if (customer.hasOpeningamt) currentCustomer.setOpeningamt(customer.getOpeningamt());
        if (customer.hasReceiveamt) currentCustomer.setReceiveamt(customer.getReceiveamt());
        if (customer.hasPaymentamt) currentCustomer.setPaymentamt(customer.getPaymentamt());
        if (customer.hasOutstandingamt) currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        if (customer.getPhone() != null) currentCustomer.setPhone(customer.getPhone());
        if (customer.getAgent() != null) currentCustomer.setAgent(customer.getAgent());

        //Verify collections
        //Orders
        if (customer.getOrders().size() > 0){
            currentCustomer.getOrders()
                    .clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = orderrepo.findById(o.getOrdnum())
                        .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " not found"));

                currentCustomer.getOrders().add(newOrder);

                //Payments
                if (o.getPayments().size() > 0) {
                    for(Payment p : o.getPayments()) {
                        Payment newPayment = paymentrepo.findById(p.getPaymentid())
                                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found"));

                        newOrder.getPayments().add(newPayment);
                    }
                }

            }

        }

        return customerrepo.save(customer);
    }

    @Transactional
    @Override
    public void delete(long custcode) {
        if (customerrepo.findById(custcode).isPresent()) {
            customerrepo.deleteById(custcode);
        } else {
            throw new EntityNotFoundException("Customer " + custcode + " not found");
        }
    }
}
