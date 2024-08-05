package uy.com.amensg.logistica.web.test;

import uy.com.amensg.logistica.util.Configuration;

public class TestConfiguration {

	public TestConfiguration() {
		System.out.println(
			Configuration.getInstance().getProperty(
				"calificacionRiesgoCrediticioBCU.institucionFinanciera.peorCalificacion"
			)
		);
	}
	
	public static void main(String[] args) {
		new TestConfiguration();
	}
}