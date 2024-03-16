package com.acme.littleUA.controller;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import com.acme.littleUA.service.headers.ServiceHeaders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/headers")
public class ControllerHeaders {
  
  @Autowired
  ServiceHeaders serviceHeaders;
  
  @GetMapping("/mirror")
  public ResponseEntity<List<RestHeaderBean>> mirror(HttpServletRequest request) {
    List<RestHeaderBean> list = serviceHeaders.findAll(request);
    return ResponseEntity.ok(list);
  }
}
