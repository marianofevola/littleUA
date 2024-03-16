package com.acme.littleUA;

import com.acme.littleUA.controller.ControllerHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LittleUaApplicationTests {

  @Autowired
  private ControllerHeaders controllerHeaders;

  @Test
  void contextLoads() {
    assertThat(controllerHeaders).isNotNull();
  }

}
