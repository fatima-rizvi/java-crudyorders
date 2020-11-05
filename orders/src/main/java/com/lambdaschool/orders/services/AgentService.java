package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentService {
    Agent save(Agent agent);

    Agent findAgentByID(long agentID);

    //Delete all agents, private from users
    void deleteAllAgents();

}
