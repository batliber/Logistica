package uy.com.amensg.logistica.webservices.external.tablero;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {

	}

	public RiesgoCreditoAmigoSTATUSBCUResponse createRiesgoCreditoAmigoSTATUSBCUResponse() {
		return new RiesgoCreditoAmigoSTATUSBCUResponse();
	}

	public BCU createBCU() {
		return new BCU();
	}

	public RiesgoCreditoAmigoSTATUSBCU createRiesgoCreditoAmigoSTATUSBCU() {
		return new RiesgoCreditoAmigoSTATUSBCU();
	}

	public RiesgoCreditoAmigoRIESGOBCUResponse createRiesgoCreditoAmigoRIESGOBCUResponse() {
		return new RiesgoCreditoAmigoRIESGOBCUResponse();
	}

	public RiesgoCreditoAmigoRIESGOBCU createRiesgoCreditoAmigoRIESGOBCU() {
		return new RiesgoCreditoAmigoRIESGOBCU();
	}

	public BCUBCUItem createBCUBCUItem() {
		return new BCUBCUItem();
	}
}