package com.acme.littleUA.repository;

import com.acme.littleUA.entity.useragent.EntityUseragent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositoryUseragent extends JpaRepository<EntityUseragent, String> {

  @Modifying
  @Transactional
  @Query(
    value = "INSERT INTO " +
      "useragent (id, useragent, updated) " +
      "VALUES (:id, :useragent, now()) " +
      "ON CONFLICT (id) " +
      "DO UPDATE SET updated=now()",
    nativeQuery = true
  )
  int upsert(
    @Param("id") String id,
    @Param("useragent") String useragent
  );
  
  EntityUseragent getFirstById(String hash);
}
