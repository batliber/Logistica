package uy.com.amensg.logistica.web.webservices.rest;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperContextResolver implements ContextResolver<Jsonb> {

	private final Jsonb jsonB;
	
	public ObjectMapperContextResolver() {
		JsonbConfig config = new JsonbConfig();
		config.setProperty(JsonbConfig.DATE_FORMAT, JsonbDateFormat.TIME_IN_MILLIS);
		config.withNullValues(false);
		config.withFormatting(true);
		
		this.jsonB = JsonbBuilder.create(config);
	}

	public Jsonb getContext(Class<?> type) {
		return jsonB;
	}
}