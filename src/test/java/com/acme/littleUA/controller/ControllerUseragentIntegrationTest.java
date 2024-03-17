package com.acme.littleUA.controller;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import com.acme.littleUA.entity.useragent.EntityUseragent;
import com.acme.littleUA.repository.RepositoryUseragent;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerUseragentIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private RepositoryUseragent repositoryUseragent;

  private final String chromeUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";

  @Test
  @Description("successful POST returns useragent created/updated")
  void useragentPOSTShouldReturnUseragentAfterPost() {
    // Set mozilla UA
    RestHeaderBean ch = new RestHeaderBean("user-agent", chromeUA);
    this.restTemplate.getRestTemplate().setInterceptors(
      Collections.singletonList((request, body, execution) -> {
        request.getHeaders()
          .add(ch.getName(), ch.getValue());
        return execution.execute(request, body);
      }));
    String url = "http://localhost:" + port + "/useragents";
    String response = this.restTemplate.postForObject(url, null, String.class);
    assertEquals(chromeUA, response);
  }

  @Test
  @Description("useragent.timestamp should be updated after another POST")
  void useragentPOSTShouldUpsertUAAfterAnotherPost() {
    // First POST to /useragent
    this.useragentPOSTShouldReturnUseragentAfterPost();
    // Get entry updated timestamp to be compared afterwards
    EntityUseragent uaEntityFirstSave = repositoryUseragent
      .getFirstById(
        new EntityUseragent(chromeUA).getId()
      );
    // Second POST
    this.useragentPOSTShouldReturnUseragentAfterPost();
    // Get entry again to compare with first one
    EntityUseragent uaEntitySecondSave = repositoryUseragent
      .getFirstById(
        new EntityUseragent(chromeUA).getId()
      );
    assertTrue(uaEntityFirstSave.getUpdated().before(uaEntitySecondSave.getUpdated()));
    assertEquals(uaEntityFirstSave.getId(), uaEntitySecondSave.getId());
    assertEquals(uaEntityFirstSave.getUseragent(), uaEntitySecondSave.getUseragent());
  }
}
