package uy.com.amensg.logistica.web.webservices.rest;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uy.com.amensg.logistica.exceptions.LogisticaException;

@Provider
public class ExceptionHandler implements ExceptionMapper<LogisticaException> {

	public Response toResponse(LogisticaException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}