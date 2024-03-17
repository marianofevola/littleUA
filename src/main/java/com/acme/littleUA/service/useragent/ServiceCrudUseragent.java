package com.acme.littleUA.service.useragent;

import com.acme.littleUA.entity.useragent.EntityUseragent;

public interface ServiceCrudUseragent {
  int upsert(EntityUseragent ua);
}
