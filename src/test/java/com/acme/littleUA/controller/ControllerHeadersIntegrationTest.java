package com.acme.littleUA.controller;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerHeadersIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Description("Expect exact number of headers and make sure a custom header is present")
  void mirroringShouldReturnCustomHeader() throws Exception {
    // Add custom header
    RestHeaderBean ch = new RestHeaderBean("my-custom-header-test", "my-custom-header-value");
    this.restTemplate.getRestTemplate().setInterceptors(
      Collections.singletonList((request, body, execution) -> {
        request.getHeaders()
          .add(ch.getName(), ch.getValue());
        return execution.execute(request, body);
      }));
    String url = "http://localhost:" + port + "/headers/mirror";
    String response = this.restTemplate.getForObject(url, String.class);
    ObjectMapper mapper = new ObjectMapper();
    List<RestHeaderBean> headers = mapper.readValue(response, new TypeReference<List<RestHeaderBean>>() {});
    // By default there are "accepts", "user-agent", "host" and "connection" headers
    assertEquals(5, headers.size());
    // make sure custom header is actually present
    assertTrue(headers.stream().anyMatch(o -> o.getName().equals(ch.getName())));
  }
}
