package uy.com.amensg.logistica.web.webservices.rest;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.jboss.resteasy.spi.Dispatcher;
import org.jboss.resteasy.spi.ResourceInvoker;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.web.entities.WebServiceRESTTO;

@Path("/WebServicesREST")
public class WebServicesREST {

	private @Context Dispatcher dispatcher;
	
	@GET
	@Path("/listAllResources")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<WebServiceRESTTO> listAllResources(){
		Collection<WebServiceRESTTO> resources = new LinkedList<WebServiceRESTTO>();
		
		ResourceMethodRegistry registry = (ResourceMethodRegistry) dispatcher.getRegistry();
		
		List<WebServiceRESTTO> toSort = new LinkedList<WebServiceRESTTO>();
		for (Map.Entry<String, List<ResourceInvoker>> entry : registry.getBounded().entrySet()) {
			for (ResourceInvoker invoker : entry.getValue()) {
				Method method = invoker.getMethod();
				
				if(method.getDeclaringClass() == getClass()) {
					continue;
				}
				
				WebServiceRESTTO webServiceRESTTO = new WebServiceRESTTO();
				webServiceRESTTO.setUri("/LogisticaWEB/RESTFacade" + entry.getKey());
				webServiceRESTTO.setMethod("/LogisticaWEB/RESTFacade" + method.getName());
				
				toSort.add(webServiceRESTTO);
			}
		}
		
		Collections.sort(toSort, new Comparator<WebServiceRESTTO>() {
			public int compare(WebServiceRESTTO arg0, WebServiceRESTTO arg1) {
				return arg0.getUri().compareTo(arg1.getUri());
			}
		});
		
		resources.addAll(toSort);
		
		return resources;
	}
}