package uy.com.amensg.logistica.web.test;

import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigo;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoRIESGOBCU;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoRIESGOBCUResponse;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoSoapPort;

public class TestWebServiceRiesgoOnline {

	public TestWebServiceRiesgoOnline() {
		RiesgoCreditoAmigoSoapPort riesgoCreditoAmigo = new RiesgoCreditoAmigo().getRiesgoCreditoAmigoSoapPort();
		
		RiesgoCreditoAmigoRIESGOBCU riesgoCreditoAmigoRIESGOBCU = new RiesgoCreditoAmigoRIESGOBCU();
		riesgoCreditoAmigoRIESGOBCU.setWdocu("33834359");
		riesgoCreditoAmigoRIESGOBCU.setWip("200.40.20.251");
		riesgoCreditoAmigoRIESGOBCU.setWpass("N3s70r-3$eV");
		riesgoCreditoAmigoRIESGOBCU.setWriesgo("PENDIENTE");
		riesgoCreditoAmigoRIESGOBCU.setWusu("NestorBCU");
		
		RiesgoCreditoAmigoRIESGOBCUResponse response = 
			riesgoCreditoAmigo.riesgobcu(riesgoCreditoAmigoRIESGOBCU);
		
		System.out.println(response.getResp());
	}
	
	public static void main(String[] args) {
		new TestWebServiceRiesgoOnline();
	}
}