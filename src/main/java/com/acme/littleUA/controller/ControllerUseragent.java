package com.acme.littleUA.controller;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import com.acme.littleUA.service.useragent.ServiceCrudUseragent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
