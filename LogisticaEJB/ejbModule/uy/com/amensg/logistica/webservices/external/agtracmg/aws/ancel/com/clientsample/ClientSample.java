package uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com.clientsample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com.GTRACMG;
import uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com.GTRACMGExecute;
import uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com.GTRACMGSoapPort;

public class ClientSample {

	public ClientSample() {
		try {
			System.out.println("***********************");
			System.out.println("Create Web Service Client...");
			GTRACMG service1 = new GTRACMG();
			System.out.println("Create Web Service...");
			GTRACMGSoapPort port1 = service1.getGTRACMGSoapPort();
			preparePort(port1);
			System.out.println("Call Web Service Operation...");
			GTRACMGExecute param = new GTRACMGExecute();
			param.setXml(
				"<WSInTRG xmlns=\"WEBSERVICES\">"
				+ "<Origen>81</Origen>"
				+ "<TrmExtId>12345678</TrmExtId>"
				+ "<WSApl>4</WSApl>"
				+ "<TpoTrmExt>320</TpoTrmExt>"
				+ "<MIDId>99111445</MIDId>"
				+ "<Agente>1</Agente>"
				+ "<Producto>105</Producto>"
				+ "<SecCaracterescolns:SecCaracteres xmlns:SecCaracterescolns=\"WEBSERVICES\" xmlns=\"\">"
				+ "<item>455</item>"
				+ "<item>100</item>"
				+ "</SecCaracterescolns:SecCaracteres>"
				+ "</WSInTRG>"
			);
			System.out.println("Server said: " + port1.execute(param).getXml());
			//Please input the parameters instead of 'null' for the upper method!
		
//			System.out.println("Create Web Service...");
//			GTRACMGSoapPort port2 = service1.getGTRACMGSoapPort();
//			preparePort(port2);
//			System.out.println("Call Web Service Operation...");
//			System.out.println("Server said: " + port2.execute(null));
			
			System.out.println("***********************");
			System.out.println("Call Over!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ClientSample();
	}
	
	public void preparePort(GTRACMGSoapPort port) 
		throws NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, 
		IOException, UnrecoverableKeyException, KeyManagementException {
		String antelGTRACUser =
			"twselared";
//			Configuration.getInstance().getProperty("antel.GTRAC.user");
		String antelGTRACPass =
			"Pass.w0rd";
//			Configuration.getInstance().getProperty("antel.GTRAC.pass");

		SSLContext sslContext = SSLContext.getInstance("TLS");
		
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
		
		sslContext.init(null, trustManagers, null);
		
//		TLSClientParameters tlsCilentParameters = new TLSClientParameters();
//		tlsCilentParameters.setDisableCNCheck(true);
//		tlsCilentParameters.setTrustManagers(trustManagers);
//		
//		BindingProvider bindingProvider = (BindingProvider) port; 
//		bindingProvider.getRequestContext().put(
//			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
//			sslContext.getSocketFactory()
//		); 
//		
//		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
//		httpConduit.getAuthorization().setUserName(antelGTRACUser);
//		httpConduit.getAuthorization().setPassword(antelGTRACPass);
//		httpConduit.setTlsClientParameters(tlsCilentParameters);
	}
}