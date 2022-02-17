package org.acme.people.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// maybe write a super short post on how to serialize but not deserialize fields - https://stackoverflow.com/questions/16019834/ignoring-property-when-deserializing
// json ignore doesn't really cut it here
@JsonIgnoreProperties(value = { "id" }, allowGetters = true)
public class Person {

  private int id;
  private String name;
  private int age;

  public Person(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public Person() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
