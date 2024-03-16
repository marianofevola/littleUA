package com.acme.littleUA.controller.rest.header;

public class RestHeaderBean {
  private String name;
  private String value;

  public RestHeaderBean() {}

  public RestHeaderBean(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public RestHeaderBean setName(String name) {
    this.name = name;
    return this;
  }

  public String getValue() {
    return value;
  }

  public RestHeaderBean setValue(String value) {
    this.value = value;
    return this;
  }

  @Override
  public String toString() {
    return String.format("name=\"%s\" value=\"%s\"", name, value);
  }
}
