package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    AgentService agentService;
//    http://localhost:2019/agents/agent/9
    @GetMapping(value = "/agent/{agentID}", produces = {"application/json"})
    public ResponseEntity<?> findAgentByID(@PathVariable long agentID) {
        Agent a = agentService.findAgentByID(agentID);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

}
