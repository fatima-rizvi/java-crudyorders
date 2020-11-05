package com.lambdaschool.orders.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//The entity allowing interactions with the agents table
@Entity
@Table(name = "agents")
public class Agent {

    //The primary key number on the agents table, long, can't be null
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long agentcode;

    //The name of the agent, a string
    @Column(unique = true,
            nullable = false)
    private String agentname;

    //The working area of the agent, a string
    private String workingarea;

    //The agent's commision, a double
    private double commission;

    //The agent's phone, a string
    private String phone;

    //The agent's country, a string
    private String country;

    //Connect agents to customers, needs a list
    //annotations
    //Key it is mapped by
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "agent", allowSetters = true)
    private List<Customer> customers = new ArrayList<>();


    public Agent() {
    }

    public Agent(String agentname, String workingarea, double commission, String phone, String country) {
        this.agentname = agentname;
        this.workingarea = workingarea;
        this.commission = commission;
        this.phone = phone;
        this.country = country;
    }

    public long getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(long agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
