package com.acme.littleUA.service.useragent;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import org.springframework.data.domain.Page;

public interface ServiceCrudUseragent {
  int upsert(EntityUseragent ua);
  Page<EntityUseragent> find(int limit);
}
