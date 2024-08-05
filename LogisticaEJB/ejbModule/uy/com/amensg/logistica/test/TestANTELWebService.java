package uy.com.amensg.logistica.test;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;

import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.DataMask;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller.DataMaskBoundary;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller.DataMaskBoundary_Service;

public class TestANTELWebService {

	public TestANTELWebService() {
		try {
			String keystorePath = 
	//				Configuration.getInstance().getProperty("keystore.path");
				"/home/amensg/Desarrollo/wildfly-21.0.0.Final/standalone/configuration/amensg.com.keystore";
			String keystorePass =
	//				Configuration.getInstance().getProperty("keystore.pass");
				"amensg2014";
			
			SSLContext sslContext = SSLContext.getInstance("TLS");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(
				new FileInputStream(keystorePath), 
				keystorePass.toCharArray()
			);
			kmf.init(
				ks, 
				keystorePass.toCharArray()
			);
			
			// Trust all certificates.
			TrustManager[] trustManagers = new TrustManager[] {
				new X509TrustManager (){
					
					public void checkClientTrusted(
						X509Certificate[] certs, String authType
					) throws CertificateException {
						
					}
	
					public void checkServerTrusted(
						X509Certificate[] certs, String authType
					) throws CertificateException {
						
					}
	
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				}
			};
			
			sslContext.init(kmf.getKeyManagers(), trustManagers, null);
			
//			TLSClientParameters tlsCilentParameters = new TLSClientParameters();
//			tlsCilentParameters.setDisableCNCheck(true);
//			tlsCilentParameters.setKeyManagers(kmf.getKeyManagers());
//			tlsCilentParameters.setTrustManagers(trustManagers);
			
			DataMaskBoundary_Service service = new DataMaskBoundary_Service();
			DataMaskBoundary port = service.getDataMaskBoundaryPort();
			
			BindingProvider bindingProvider = (BindingProvider) port; 
			bindingProvider.getRequestContext().put(
				"com.sun.xml.ws.transport.https.client.SSLSocketFactory",
//				"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
				sslContext.getSocketFactory()
			);
			
//			HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
//			httpConduit.getAuthorization().setUserName("rivergreen");
//			httpConduit.getAuthorization().setPassword("rg2021rg");
//			httpConduit.setTlsClientParameters(tlsCilentParameters);
			
			DataMask response = port.getDataMask("1234");
			System.out.println(
				"Phone number: " + response.getPhoneNumber() + System.lineSeparator()
				+ "Serial: " + response.getSerial() + System.lineSeparator()
				+ "Token" + response.getToken()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TestANTELWebService();
	}
}