package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.pruebas.notify_test;

import java.io.StringReader;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType.Message;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.Notify;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.NotificationBrokerNotify;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.NotifyResponse;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.PublishService;

public class App 
{
	
	//url del sistema de notificaciones en intg
	private static final String URL_NOTIF = "https://wsi-intg.in.iantel.com.uy/notification/brokerInterface/notify";
	
	
	//Información vinculada a headers de WS-Addressing
	private static final String ADDRESSING_FROM = "urn:antel:mdm:system:rgn";
	private static final String ADDRESSING_TO = "urn:antel:mdm:system:notification";
	private static final String ADDRESSING_ACTION = "http://docs.oasis-open.org/wsn/bw-2/NotificationConsumer/Notify";
	
	//Un tópico "vive" en un namespace, en este caso el de "Equipment"
	private static final String NAMESPACE = "http://ns:antel.com.uy/topic/equipment";
	private static final String NAMESPACE_PREFIX = "Equipment";
	
	//Tópico al que se mandan las noticias
	private static final String TOPIC = "Equipment:Delivery/rivergreen/state";
	private static final String DIALECT = "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete";
	
	
	private static SSLSocketFactory sslSocketFactory = null;
	
	public static void main( String[] args ) throws Exception {
		//Creación del namespace
		List<NS> nsList = new ArrayList<NS>();
		NS ns = new NS();
		ns.setPrefix(NAMESPACE_PREFIX);
		ns.setNamespace(NAMESPACE);
		nsList.add(ns);	
		
		
		//Contenido de la noticia
		String xml = 
			"<ticketNotification>"
				+ "<correlationTicketid>idTicketIZI</correlationTicketid>"
				+ "<record xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"processRelated\">"
					+ "<recordDate>2019-12-18T15:09:44.763-03:00</recordDate>"
					+ "<recordType>ProcessRelated</recordType>"
					+ "<sourceSystem>antel:mdm:system:rg</sourceSystem>"
					+ "<sourceSystemUser>"
						+ "<id>usuRg</id>"
						+ "<label>Rivergreen</label>"
					+ "</sourceSystemUser>"
					+ "<actualProcessState>closed</actualProcessState>"
					+ "<businessProcess>Gestionar Reclamo</businessProcess>"
					+ "<businessUnit>Encomiendas</businessUnit>"
					+ "<characteristics>"
						+ "<name>completionCode</name>"
						+ "<type>STRING</type>"
						+ "<value>Resolved/Cancel</value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>completionCodeDescription</name>"
						+ "<type>STRING</type>"
						+ "<value>Entregado/Rechazado</value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>completionSubCode</name>"
						+ "<type>STRING</type>"
						+ "<value> PickUp / Delivered/ NotDelivered </value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>completionSubCodeDescription</name>"
						+ "<type>STRING</type>"
						+ "<value>Entrega en Centro Logistico/Entrega en Domicilio / Se cancela autosustitución</value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>completionLabel</name>"
						+ "<type>STRING</type>"
						+ "<value>Comentario</value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>nombreReceptor</name>"
						+ "<type>STRING</type>"
						+ "<value>nombreReceptor</value>"
					+ "</characteristics>"
					+ "<characteristics>"
						+ "<name>documentoReceptor</name>"
						+ "<type>STRING</type>"
						+ "<value>documentoReceptor</value>"
					+ "</characteristics>"
					+ "<taskType>completion</taskType>"
				+ "</record>"
				+ "<ticketId>idRiverGreen</ticketId>"
			+ "</ticketNotification>";
		
		Document document = createDocument(xml);
		
		Notify notice = new Notify();
		NotificationMessageHolderType mensaje = new NotificationMessageHolderType();
		Message text = new Message();
		
		//Seteamos el contenido
		Element body = document.getDocumentElement();
		text.setAny(body);
		mensaje.setMessage(text);
		
		//Seteamos info vinculada al tópico
		TopicExpressionType topicExpression = new TopicExpressionType();
		topicExpression.setDialect(DIALECT);
		topicExpression.getContent().add(TOPIC);
		mensaje.setTopic(topicExpression);

		
		W3CEndpointReferenceBuilder producerBuilder = new W3CEndpointReferenceBuilder();
		producerBuilder.address(ADDRESSING_FROM);
		mensaje.setProducerReference(producerBuilder.build());
	
		notice.getNotificationMessage().add(mensaje);
		
		PublishService service = new PublishService();
		NotificationBrokerNotify port = service.getPublishPort();
		BindingProvider bp = (BindingProvider) port;

		
		Binding binding = bp.getBinding();		
		List<Handler> handlerList = binding.getHandlerChain();
		
		//Seteamos headers de addressing
		WSAHandler handler = new WSAHandler();
		handler.setFrom(ADDRESSING_FROM);
		handler.setTo(ADDRESSING_TO);
		handler.setMessageID(UUID.randomUUID().toString());
		handler.setNamespace(nsList);
		handler.setWsaAction(ADDRESSING_ACTION);
		handlerList.add(handler);
		binding.setHandlerChain(handlerList);
		
//		SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
//		
//		bp.getRequestContext().put(
//			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
//			sslSocketFactory
//		);
		
//		//Seteo endpoint
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, URL_NOTIF);
		
		SSLContext sslContext = SSLContext.getInstance("TLS");
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		
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
//		httpConduit.setTlsClientParameters(tlsCilentParameters);
		
		//Manda noticia
		NotifyResponse response = port.notify(notice);
		
		System.out.println(response.getResponse());
	}

	private static Document createDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
//		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		Document document = builder.parse(new InputSource(new StringReader(xml)));
		return document;
	}
	
	private static SSLSocketFactory getSSLSocketFactory() throws Exception {
		if (sslSocketFactory == null) {
			sslSocketFactory = new NotifSSLSocketFactory();
		}
		return sslSocketFactory;
	}
}