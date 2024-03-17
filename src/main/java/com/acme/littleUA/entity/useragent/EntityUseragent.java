package com.acme.littleUA.entity.useragent;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

@Entity
@Table(name = "useragent")
public class EntityUseragent {

  @Id
  private String id;
  private String useragent;
  
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Rome")
  private Timestamp updated;

  public EntityUseragent() {
  }

  public EntityUseragent(String useragent) {
    this.useragent = useragent;
    this.id = generateHash();
  }

  public String getId() {
    return id;
  }

  public EntityUseragent setId(String id) {
    this.id = id;
    return this;
  }

  public String getUseragent() {
    return useragent;
  }

  public EntityUseragent setUseragent(String useragent) {
    this.useragent = useragent;
    return this;
  }

  public Timestamp getUpdated() {
    return updated;
  }

  public EntityUseragent setUpdated(Timestamp updated) {
    this.updated = updated;
    return this;
  }

  private String generateHash() {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(
        this.useragent.getBytes(StandardCharsets.UTF_8)
      );
      char[] encode = Hex.encode(hash);
      return new String(encode);
    } catch (NoSuchAlgorithmException ex) {
      return "";
    }

  }

  @Override
  public String toString() {
    return String.format("id=%s, useragent=%s, updated=%s", id, useragent, updated);
  }
}
