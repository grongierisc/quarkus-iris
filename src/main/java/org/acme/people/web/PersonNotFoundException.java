package org.acme.people.web;

public class PersonNotFoundException extends RuntimeException {

  public PersonNotFoundException(int id) {
    super("Person with " + id + " does not exist!");
  }
}
