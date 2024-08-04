package uy.com.amensg.logistica.webservices.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import uy.com.amensg.logistica.exceptions.LogisticaException;

@Provider
public class ExceptionHandler implements ExceptionMapper<LogisticaException> {

	public Response toResponse(LogisticaException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}