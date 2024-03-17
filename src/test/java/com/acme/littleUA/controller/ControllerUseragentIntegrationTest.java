package com.acme.littleUA.controller;

import com.acme.littleUA.controller.rest.header.RestHeaderBean;
import com.acme.littleUA.entity.useragent.EntityUseragent;
import com.acme.littleUA.repository.RepositoryUseragent;
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

import static java.lang.Thread.sleep;
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
    // First POST to /useragents
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
  
  @Test
  @Description("should only return the last 10 useragents updated")
  public void useragentGETShouldReturnMax10Results() throws Exception {
    // Firstly save 11 useragents
    for (int i=1; i<=10; i++) {
      String ua = String.format("Mozilla/5.0 Testing %d", i);
      repositoryUseragent
        .upsert(
          new EntityUseragent(ua).getId(),
          ua
        );
    }
    String url = "http://localhost:" + port + "/useragents";
    String response = this.restTemplate.getForObject(url, String.class);
    ObjectMapper mapper = new ObjectMapper();
    List<EntityUseragent> useragents = mapper.readValue(response, new TypeReference<List<EntityUseragent>>() {});
    assertEquals(10, useragents.size());
  }
  
  @Test
  @Description("first useragent is the last updated")
  public void useragentGETShouldReturnLastUpdatedFirst() throws Exception {
    // Firstly save 2 useragents
    for (int i=1; i<=2; i++) {
      String ua = String.format("Mozilla/5.0 Testing %d", i);
      repositoryUseragent
        .upsert(
          new EntityUseragent(ua).getId(),
          ua
        );
      // sleep of 1 second ensures the next `useragent.updated` timestamp
      // is always after at least one second the previous one
      sleep(1000);
    }
    // Get useragents and make sure the first is the last updated
    String url = "http://localhost:" + port + "/useragents";
    String response = this.restTemplate.getForObject(url, String.class);
    ObjectMapper mapper = new ObjectMapper();
    List<EntityUseragent> useragents = mapper.readValue(response, new TypeReference<List<EntityUseragent>>() {});
    assertTrue(
      useragents.get(0).getUpdated().after(useragents.get(1).getUpdated()),
      "First useragent is not the last updated!"
    );
  }
}
