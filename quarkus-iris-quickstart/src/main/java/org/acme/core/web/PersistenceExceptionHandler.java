package org.acme.core.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.acme.core.db.PersistenceException;

@Provider
public class PersistenceExceptionHandler implements ExceptionMapper<PersistenceException> {

  @Override
  public Response toResponse(PersistenceException exception) {
    return Response.serverError().build();
  }
}
