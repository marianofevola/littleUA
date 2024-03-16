package com.acme.littleUA.service.headers;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ServiceHeaders {
  List<RestHeaderBean> findAll(HttpServletRequest request);
}
