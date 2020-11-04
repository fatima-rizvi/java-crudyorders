package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    //The primary key on the customers' table, long, not null
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long custcode;

    //Customer name, string, not null
    @Column(nullable = false)
    private String custname;

    //Customer city, string
    private String custcity;

    //Customer working area, string
    private String workingarea;

    //Customer country, string
    private String custcountry;

    //customer grade, string
    private String grade;

    // customer opening amount, double
    private double openingamt;

    // customer receiving amount, double
    private double receiveamt;

    // customer payment amount, double
    private double paymentamt;

    // customer outstanding amount, double
    private double outstandingamt;

    //customer phone string
    private String phone;

    // foreign agent key attached to customer, long foreign key, not null, (agentcode)
    //Many to one relationship to Agents, here are the annotations
    //Requires a join column
    @ManyToOne
    @JoinColumn(name = "agentcode", nullable = false)
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Agent agent;

    // One to many relationship with orders
    //Requires a list
    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "customer", allowSetters = true)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String custname, String custcity, String workingarea, String custcountry, String grade, double openingamt, double receiveamt, double paymentamt, double outstandingamt, String phone, Agent agent) {
        this.custname = custname;
        this.custcity = custcity;
        this.workingarea = workingarea;
        this.custcountry = custcountry;
        this.grade = grade;
        this.openingamt = openingamt;
        this.receiveamt = receiveamt;
        this.paymentamt = paymentamt;
        this.outstandingamt = outstandingamt;
        this.phone = phone;
        this.agent = agent;
    }

    public long getCustcode() {
        return custcode;
    }

    public void setCustcode(long custcode) {
        this.custcode = custcode;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getOpeningamt() {
        return openingamt;
    }

    public void setOpeningamt(double openingamt) {
        this.openingamt = openingamt;
    }

    public double getReceiveamt() {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt) {
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt() {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt) {
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt() {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt) {
        this.outstandingamt = outstandingamt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
