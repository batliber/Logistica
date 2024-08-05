package uy.com.amensg.logistica.web.test;

import uy.com.amensg.logistica.webservices.external.bicsa.Consulta;
import uy.com.amensg.logistica.webservices.external.bicsa.ConsultaSoap;

public class BICSAClientSample {

	public static void main(String[] args) {
		System.out.println("***********************");
		System.out.println("Create Web Service Client...");
		Consulta service1 = new Consulta();
		System.out.println("Create Web Service...");
		ConsultaSoap port1 = service1.getConsultaSoap12();
//		System.out.println("Call Web Service Operation...");
//		System.out.println("Server said: " + port1.obtenerPersona(Integer.parseInt(args[0]), null, null, null));
//		// Please input the parameters instead of 'null' for the upper method!

		System.out.println("Server said: " + port1.listadoPaises());
		
//		System.out.println("Server said: " + port1.obtenerPersonaPDF(Integer.parseInt(args[1]), null, null, null));
//		// Please input the parameters instead of 'null' for the upper method!

		System.out.println("Server said: " + port1.listadoTipoDocumentos());
		
		System.out.println("***********************");
		System.out.println("Call Over!");
	}
}