package com.acme.littleUA.controller;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import com.acme.littleUA.service.useragent.ServiceCrudUseragent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerUseragent {

  @Autowired
  ServiceCrudUseragent serviceCrudUseragent;

  @PostMapping("/useragents")
  public ResponseEntity<String> useragent(HttpServletRequest request) {
    EntityUseragent ua = new EntityUseragent(request.getHeader("user-agent"));
    if (serviceCrudUseragent.upsert(ua) == 1) {
      return ResponseEntity.ok(ua.getUseragent());
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/useragents")
  public ResponseEntity<List<EntityUseragent>> useragentList() {
    return ResponseEntity.ok(serviceCrudUseragent.find(10).getContent());
  }
}
