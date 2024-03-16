package com.acme.littleUA.service.headers;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceHeadersImpl implements ServiceHeaders {

  @Override
  public List<RestHeaderBean> findAll(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    List<RestHeaderBean> list = new ArrayList<>();
    if (Objects.isNull(headerNames)) {
      return null;
    }
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      list.add( new RestHeaderBean(name, request.getHeader(name)));
    }
    return list;
  }
}
