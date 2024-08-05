package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.DataMask;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller.DataMaskBoundary;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller.DataMaskBoundary_Service;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType.Message;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.Notify;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.NotificationBrokerNotify;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.NotifyResponse;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.PublishService;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.pruebas.notify_test.NS;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.pruebas.notify_test.WSAHandler;

@Stateless
public class ANTELBean implements IANTELBean {
	
	//Información vinculada a headers de WS-Addressing
	private static final String ADDRESSING_TO = "urn:antel:mdm:system:notification";
	private static final String ADDRESSING_ACTION = "http://docs.oasis-open.org/wsn/bw-2/NotificationConsumer/Notify";
	
	//Un tópico "vive" en un namespace, en este caso el de "Equipment"
	private static final String NAMESPACE = "http://ns:antel.com.uy/topic/equipment";
	private static final String NAMESPACE_PREFIX = "Equipment";
	
	//Tópico al que se mandan las noticias
//	private static final String TOPIC = "Equipment:Delivery/rivergreen/state";
	private static final String TOPIC = "clm:close/clm:rivergreen";
	private static final String DIALECT = "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete";
	
	public Long notificarInstalacion(
		String idTicket, String telefono, String serieONT, int idTypeData, String info
	) {
		Long result = null;
		
		try {
			DataMaskBoundary_Service service = new DataMaskBoundary_Service();
			DataMaskBoundary port = service.getDataMaskBoundaryPort();
			
			preparePort(port);
			
			result = 
				Long.valueOf(
					port.addDataMask(
						idTicket, 
						serieONT, 
						telefono,
						idTypeData,
						info
					)
				);
			
			System.out.println(
				"idTicket: " + idTicket + "\n"
				+ "telefono: " + telefono + "\n"
				+ "serieONT: " + serieONT + "\n"
				+ "idTypeData: " + idTypeData + "\n"
				+ "info: " + info
			);
		} catch (Exception e) {
			e.printStackTrace();
			result = Long.valueOf(-500);
		}
		
		return result;
	}

	public DataMask obtenerDatosANTEL(String idTicket) {
		DataMask result = null;
		
		try {
			DataMaskBoundary_Service service = new DataMaskBoundary_Service();
			DataMaskBoundary port = service.getDataMaskBoundaryPort();
			
			preparePort(port);
			
			result = port.getDataMask(idTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public NotifyResponse publicarNoticiaIZI(
		String idTicketIZI, String recordDate, String completionCode, String completionCodeDescription, 
		String completionSubCode, String completionSubCodeDescription, String completionLabel, 
		String nombreReceptor, String documentoReceptor, String idRiverGreen
	) {
		NotifyResponse result = null;
		
		try {
			//Creación del namespace
			List<NS> nsList = new ArrayList<NS>();
			NS ns = new NS();
			ns.setPrefix(NAMESPACE_PREFIX);
			ns.setNamespace(NAMESPACE);
			nsList.add(ns);
			
			String sourceSystem = 
				Configuration.getInstance().getProperty("antel.NotificationBroker.sourceSystem");
			String usuario = "usuRg";
			String labelUsuario = "Rivergreen";
			
			//Contenido de la noticia
			String xml = 
				"<ticketNotification>"
					+ "<correlationTicketid>" + idTicketIZI + "</correlationTicketid>"
					+ "<record xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"processRelated\">"
						+ "<recordDate>" + recordDate + "</recordDate>"
						+ "<recordType>ProcessRelated</recordType>"
						+ "<sourceSystem>" + sourceSystem  + "</sourceSystem>"
						+ "<sourceSystemUser>"
							+ "<id>" + usuario + "</id>"
							+ "<label>" + labelUsuario + "</label>"
						+ "</sourceSystemUser>"
						+ "<actualProcessState>closed</actualProcessState>"
						+ "<businessProcess>Gestionar Reclamo</businessProcess>"
						+ "<businessUnit>Encomiendas</businessUnit>"
						+ "<characteristics>"
							+ "<name>completionCode</name>"
							+ "<type>STRING</type>"
							+ "<value>" + completionCode + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>completionCodeDescription</name>"
							+ "<type>STRING</type>"
							+ "<value>" + completionCodeDescription  + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>completionSubCode</name>"
							+ "<type>STRING</type>"
							+ "<value>" + completionSubCode + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>completionSubCodeDescription</name>"
							+ "<type>STRING</type>"
							+ "<value>" + completionSubCodeDescription + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>completionLabel</name>"
							+ "<type>STRING</type>"
							+ "<value>" + completionLabel + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>nombreReceptor</name>"
							+ "<type>STRING</type>"
							+ "<value>" + nombreReceptor + "</value>"
						+ "</characteristics>"
						+ "<characteristics>"
							+ "<name>documentoReceptor</name>"
							+ "<type>STRING</type>"
							+ "<value>" + documentoReceptor + "</value>"
						+ "</characteristics>"
						+ "<taskType>completion</taskType>"
					+ "</record>"
					+ "<ticketId>" + idRiverGreen + "</ticketId>"
				+ "</ticketNotification>";
			
//			System.out.println(xml);
			
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
			
			String addressingFrom = "urn:" + sourceSystem;
			producerBuilder.address(addressingFrom);
			mensaje.setProducerReference(producerBuilder.build());
		
			notice.getNotificationMessage().add(mensaje);
			
			PublishService service = new PublishService();
			NotificationBrokerNotify port = service.getPublishPort();
			
			preparePort(port, nsList);
			
			//Manda noticia
			result = port.notify(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String notificarAPIStock(Collection<String> series) {
		String result = null;
		
		try {
			String url = Configuration.getInstance().getProperty("antel.stockAPI.URL");
			String operationType = Configuration.getInstance().getProperty("antel.stockAPI.operationType");
			String originSystem = Configuration.getInstance().getProperty("antel.stockAPI.originSystem");
			String businessProcessName = Configuration.getInstance().getProperty("antel.stockAPI.businessProcessName");
			String businessTaskName = Configuration.getInstance().getProperty("antel.stockAPI.businessTaskName");
			String stockPlace = Configuration.getInstance().getProperty("antel.stockAPI.stockPlace");
			String sku = Configuration.getInstance().getProperty("antel.stockAPI.sku");
			
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			Date date = GregorianCalendar.getInstance().getTime();
			
//			JsonArrayBuilder serialNumbers = Json.createArrayBuilder();
//			
//			for (String serie : series) {
//				serialNumbers.add(serie);
//			}
//			
//			UUID uuid = UUID.randomUUID();
//			
//			JsonObject params = Json.createObjectBuilder()
//				.add("operationType", operationType)
//				.add("operationDate", "")
//				.add("originSystem", originSystem)
//				.add("correlationId", uuid.toString())
//				.add("businessProcessName", businessProcessName)
//				.add("businessTaskName", businessTaskName)
//				.add("stockItems", Json.createArrayBuilder()
//					.add(Json.createObjectBuilder()
//						.add("sku", sku)
//						.add("quantity", series.size())
//						.add("unit", "unit")
//						.add("stockPlace", stockPlace)
//						.add("serialNumbers", serialNumbers)
//					)
//				)
//				.build();
//			
//			System.out.println(params.toString());
//			
//			HttpClient client = 
//				HttpClient.newBuilder()
//					.sslContext(initSSLContext())
//					.build();
//			
//			HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create(url))
//				.header("Content-Type", "application/json")
//				.POST(HttpRequest.BodyPublishers.ofString(params.toString()))
//				.build();
//
//			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			result = "";
			for (String serie : series) {
				UUID uuid = UUID.randomUUID();
				
				JsonArrayBuilder serialNumbers = Json.createArrayBuilder();
				serialNumbers.add(serie);
				
				JsonObject params = Json.createObjectBuilder()
					.add("operationType", operationType)
					.add("operationDate", "")
					.add("originSystem", originSystem)
					.add("correlationId", uuid.toString())
					.add("businessProcessName", businessProcessName)
					.add("businessTaskName", businessTaskName)
					.add("stockItems", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("sku", sku)
							.add("quantity", 1)
							.add("unit", "unit")
							.add("stockPlace", stockPlace)
							.add("serialNumbers", serialNumbers)
						)
					)
					.build();
				
				System.out.println(params.toString());
				
				HttpClient client = 
					HttpClient.newBuilder()
						.sslContext(initSSLContext())
						.build();
				
				HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(params.toString()))
					.build();
				
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				
				System.out.println(response.body());
				
				result += response.body();
			}
			
//			result = response.body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String obtenerAccessToken() {
		String result = null;
		
		try {
			String urlString = 
				Configuration.getInstance().getProperty("antel.OIDC.URL");
			String antelOIDCClientId = 
				Configuration.getInstance().getProperty("antel.OIDC.clientId");
			String antelOIDCClientSecret = 
				Configuration.getInstance().getProperty("antel.OIDC.clientSecret");
			
			String keystorePath = 
				Configuration.getInstance().getProperty("keystore.path");
			String keystorePass =
				Configuration.getInstance().getProperty("keystore.pass");
				
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
			
			HttpsURLConnection urlConnection = 
				(HttpsURLConnection) new URL(urlString + "?grant_type=client_credentials&client_id=" + antelOIDCClientId + "&client_secret=" + antelOIDCClientSecret).openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
			// Trust all hosts names
			urlConnection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession sslSession) {
					return true;
				}
			});
			urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
			urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			urlConnection.setRequestProperty("Accept", "application/json");
			
	//		String encodedCredential = 
	//			Base64.getEncoder().encodeToString(
	//				(antelOIDCUser + ":" + antelOIDCPass).getBytes()
	//			);
	//		urlConnection.setRequestProperty("Authorization", "Basic " + encodedCredential);
			
//			String jsonInputString = 
//				"{"
//					+ "	\"grant_type\": \"client_credentials\","
//					+ " \"client_id\": \"" + antelOIDCClientId + "\","
//					+ " \"client_secret\": \"" + antelOIDCClientSecret + "\""
//				+ "}";
//			
//	//			System.out.println(jsonInputString);
//			
//			byte[] input = jsonInputString.getBytes("utf-8");
			
			urlConnection.connect();
			
//			OutputStream outputStream = urlConnection.getOutputStream();
//			outputStream.write(input, 0, input.length);
			
			BufferedReader bufferedReader =
				new BufferedReader(
					new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"
					)
				);
			
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = bufferedReader.readLine()) != null) {
				response.append(responseLine.trim());
			}
			
			result = response.toString();
			
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String publicarNota(String idTicket, String texto) {
		String result = null;
		
		try {
			String url = 
				Configuration.getInstance().getProperty("antel.SURAPI.URLPrefix")
				+ idTicket + "/records/notes";
			String businessUnit = 
				Configuration.getInstance().getProperty("antel.SURAPI.businessUnit");
			String relyingParty =
				Configuration.getInstance().getProperty("antel.SURAPI.relyingParty");
			String sourceSystemUserId =
				Configuration.getInstance().getProperty("antel.SURAPI.sourceSystemUserId");
			String sourceSystemUserLabel =
				Configuration.getInstance().getProperty("antel.SURAPI.sourceSystemUserLabel");
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			JsonObject params = Json.createObjectBuilder()
				.add("businessUnit", businessUnit)
				.add("recordScope", "public")
				.add("recordDate", format.format(hoy))
				.add("text", texto)
				.build();
			
			System.out.println(params.toString());
			
			HttpClient client = 
				HttpClient.newBuilder()
					.sslContext(initSSLContext())
					.build();
			
			String bearer = "test";
			
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("relyingParty", relyingParty) 
				.header("sourceSystemUserId", sourceSystemUserId)
				.header("sourceSystemUserLabel" , sourceSystemUserLabel) 
				.header("Authorization", "Bearer '" + bearer  + "'")
				.POST(HttpRequest.BodyPublishers.ofString(params.toString()))
				.build();
	
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			result = response.body();
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static Document createDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
//		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		Document document = builder.parse(new InputSource(new StringReader(xml)));
		return document;
	}
	
	private void preparePort(DataMaskBoundary port) 
		throws NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, 
		IOException, UnrecoverableKeyException, KeyManagementException {
		String keystorePath = 
			Configuration.getInstance().getProperty("keystore.path");
		String keystorePass =
			Configuration.getInstance().getProperty("keystore.pass");
		String antelDataMaskBoundaryUser = 
			Configuration.getInstance().getProperty("antel.DataMaskBoundary.user");
		String antelDataMaskBoundaryPass = 
			Configuration.getInstance().getProperty("antel.DataMaskBoundary.pass");
		
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
		
		TLSClientParameters tlsCilentParameters = new TLSClientParameters();
		tlsCilentParameters.setDisableCNCheck(true);
		tlsCilentParameters.setKeyManagers(kmf.getKeyManagers());
		tlsCilentParameters.setTrustManagers(trustManagers);
		
		BindingProvider bindingProvider = (BindingProvider) port; 
		bindingProvider.getRequestContext().put(
			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
			sslContext.getSocketFactory()
		); 
		
		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
		httpConduit.getAuthorization().setUserName(antelDataMaskBoundaryUser);
		httpConduit.getAuthorization().setPassword(antelDataMaskBoundaryPass);
		httpConduit.setTlsClientParameters(tlsCilentParameters);
	}

	private void preparePort(NotificationBrokerNotify port, List<NS> nsList) throws NoSuchAlgorithmException, KeyManagementException {
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
		
		TLSClientParameters tlsCilentParameters = new TLSClientParameters();
		tlsCilentParameters.setDisableCNCheck(true);
		tlsCilentParameters.setTrustManagers(trustManagers);
		
		BindingProvider bindingProvider = (BindingProvider) port;
		
		Binding binding = bindingProvider.getBinding();
		List<Handler> handlerList = binding.getHandlerChain();
		
//		handlerList.add(new SOAPHandler<SOAPMessageContext>() {
//
//			public boolean handleMessage(SOAPMessageContext context) {
//				SOAPMessage message = context.getMessage();
//				try {
//					StringWriter stringWriter = new StringWriter();
//					
//					TransformerFactory.newInstance().newTransformer().transform(
//						new DOMSource(message.getSOAPPart()),
//						new StreamResult(stringWriter)
//					);
//					
//					System.out.println(stringWriter.toString());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				return true;
//			}
//
//			public boolean handleFault(SOAPMessageContext context) {
//				SOAPMessage message = context.getMessage();
//				try {
//					StringWriter stringWriter = new StringWriter();
//					
//					TransformerFactory.newInstance().newTransformer().transform(
//						new DOMSource(message.getSOAPPart()),
//						new StreamResult(stringWriter)
//					);
//					
//					System.out.println(stringWriter.toString());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return true;
//			}
//
//			public void close(MessageContext context) {
//				
//			}
//
//			public Set<QName> getHeaders() {
//				return null;
//			}
//		});
		
		//Seteamos headers de addressing
		WSAHandler handler = new WSAHandler();
		
		String sourceSystem = 
			Configuration.getInstance().getProperty("antel.NotificationBroker.sourceSystem");
		String addressingFrom = "urn:" + sourceSystem;
		handler.setFrom(addressingFrom);
		
		handler.setTo(ADDRESSING_TO);
		handler.setMessageID(UUID.randomUUID().toString());
		handler.setNamespace(nsList);
		handler.setWsaAction(ADDRESSING_ACTION);
		handlerList.add(handler);
		
		binding.setHandlerChain(handlerList);
		
		//Seteo endpoint
		bindingProvider.getRequestContext().put(
			BindingProvider.ENDPOINT_ADDRESS_PROPERTY, 
			Configuration.getInstance().getProperty("antel.NotificationBroker.URL")
		);
		
		bindingProvider.getRequestContext().put(
			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
			sslContext.getSocketFactory()
		); 
		
		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
		httpConduit.setTlsClientParameters(tlsCilentParameters);
	}

	private SSLContext initSSLContext() {
		SSLContext result = null;
		try {
			result = SSLContext.getInstance("TLS");
		
			String keystorePath = 
				Configuration.getInstance().getProperty("keystore.path");
			String keystorePass =
				Configuration.getInstance().getProperty("keystore.pass");
			
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
			
			result.init(kmf.getKeyManagers(), trustManagers, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}