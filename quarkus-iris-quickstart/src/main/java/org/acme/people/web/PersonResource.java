package org.acme.people.web;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.people.db.PersonRepository;
import org.acme.people.model.Person;

@Path("/people")
public class PersonResource {

  private final PersonRepository personRepository;

  public PersonResource(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  // using `Response` requires extra code to enable runtime reflection
  // this is important if running against GraalVM
  // the serialization type can infer the serialization types at compile time
  // meaning that nothing needs to be done when the code below is used
  // maybe cover this in a separate post?
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> all() {
    return personRepository.findAll();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person get(@PathParam("id") int id) {
    return personRepository.findById(id);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Person post(Person person) {
    return personRepository.insert(
        new Person(personRepository.maxId()+1, person.getName(), person.getAge()));
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person put(@PathParam("id") int id, Person person) {
    if (personRepository.findById(id) == null) {
      throw new PersonNotFoundException(id);
    }
    return personRepository.update(new Person(id, person.getName(), person.getAge()));
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public void delete(@PathParam("id") int id) {
    if (personRepository.findById(id) == null) {
      throw new PersonNotFoundException(id);
    }
    personRepository.deleteById(id);
  }
}
