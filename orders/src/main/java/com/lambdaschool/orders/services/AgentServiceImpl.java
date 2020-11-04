package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentservice")
public class AgentServiceImpl implements AgentService{

    @Autowired
    AgentsRepository agentrepo;

    @Transactional
    @Override
    public Agent save(Agent agent) {
        return agentrepo.save(agent);
    }

    @Override
    public Agent findAgentByID(long agentID) {
        return agentrepo.findById(agentID)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + agentID + " not found."));
    }
}
