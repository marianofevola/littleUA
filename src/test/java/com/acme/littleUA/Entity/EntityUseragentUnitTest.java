package com.acme.littleUA.Entity;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityUseragentUnitTest {
  
  @Test
  public void useragentIdHashShouldNotChange() {
    String chromeUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
    EntityUseragent entityUseragent = new EntityUseragent(chromeUA);
    EntityUseragent entityUseragent1 = new EntityUseragent(chromeUA);
    EntityUseragent entityUseragent2 = new EntityUseragent(chromeUA);
    assertEquals(entityUseragent.getId(), entityUseragent1.getId());
    assertEquals(entityUseragent1.getId(), entityUseragent2.getId());
    
    String firefoxUA = "Mozilla/5.0 (X11; Linux i686; rv:10.0) Gecko/20100101 Firefox/10.0";
    entityUseragent = new EntityUseragent(firefoxUA);
    entityUseragent1 = new EntityUseragent(firefoxUA);
    entityUseragent2 = new EntityUseragent(firefoxUA);
    assertEquals(entityUseragent.getId(), entityUseragent1.getId());
    assertEquals(entityUseragent1.getId(), entityUseragent2.getId());
  }
}
