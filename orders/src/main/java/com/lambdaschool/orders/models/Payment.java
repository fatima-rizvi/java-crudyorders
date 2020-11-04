package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment {

    //primary id, payment id, not null, long
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long paymentid;

    //payment type, string
    @Column(unique = true,
            nullable = false)
    private String type;

    //Payments has a many to many relationship with orders
    @ManyToMany(mappedBy = "payments")
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    public Payment() {
    }

    public Payment(String type) {
        this.type = type;
    }

    public long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(long paymentid) {
        this.paymentid = paymentid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
