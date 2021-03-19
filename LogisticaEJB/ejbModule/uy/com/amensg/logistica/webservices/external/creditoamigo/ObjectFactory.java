
package uy.com.amensg.logistica.webservices.external.creditoamigo;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
	
	}

	public HelloWorld createHelloWorld() {
		return new HelloWorld();
	}

	public HelloWorldResponse createHelloWorldResponse() {
		return new HelloWorldResponse();
	}

	public BicsaAdd createBicsaAdd() {
		return new BicsaAdd();
	}

	public BicsaAddResponse createBicsaAddResponse() {
		return new BicsaAddResponse();
	}
}