package com.acme.littleUA.service.useragent;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import com.acme.littleUA.repository.RepositoryUseragent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCrudUseragentImpl implements ServiceCrudUseragent {

  @Autowired
  private RepositoryUseragent repositoryUseragent;

  @Override
  public int upsert(EntityUseragent ua) {
    return repositoryUseragent.upsert(ua.getId(), ua.getUseragent());
  }
}
