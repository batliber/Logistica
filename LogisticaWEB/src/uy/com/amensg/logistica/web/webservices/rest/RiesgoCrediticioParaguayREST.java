package uy.com.amensg.logistica.web.webservices.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguay;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.entities.ImportacionArchivoRiesgoCrediticioParaguayTO;
import uy.com.amensg.logistica.web.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoImportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoNotificarCreditoAmigoPYTO;

@Path("/RiesgoCrediticioParaguayREST")
public class RiesgoCrediticioParaguayREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
				
				result = iRiesgoCrediticioParaguayBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					RiesgoCrediticioParaguay riesgoCrediticioParaguay = (RiesgoCrediticioParaguay) object;
					
					riesgoCrediticioParaguay.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					riesgoCrediticioParaguay.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
				
				result = iRiesgoCrediticioParaguayBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RiesgoCrediticioParaguay getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
				
				result = iRiesgoCrediticioParaguayBean.getById(id);
				
				result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
				result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getInformacionRiesgoCrediticio")
	@Produces("application/json")
	public RiesgoCrediticioParaguay getInformacionRiesgoCrediticio(@Context UriInfo uriInfo) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Integer maximoTiempoProcesadoDias = Integer.parseInt(
				Configuration.getInstance().getProperty("riesgoCrediticio.maximoTiempoProcesadoDias")
			);
			
			gregorianCalendar.add(
				GregorianCalendar.DATE, 
				-1 * maximoTiempoProcesadoDias
			);
			
			Date fechaMaximaVigencia = gregorianCalendar.getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			
			String documento = null;
			String fNac = null;
			String situacion = null;
			
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			if (queryParams.size() >= 3) {
				documento = queryParams.get("documento").get(0);
				fNac = queryParams.get("fnac").get(0);
				situacion = queryParams.get("situacion").get(0);
			}
			
			Date fechaNacimiento = format.parse(fNac);
			
			Long situacionRiesgoCrediticioId = Long.parseLong(situacion);
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguay = 
				iRiesgoCrediticioParaguayBean
					.getLastByDocumentoFechaNacimientoSituacionRiesgoCrediticioParaguayId(
						documento, fechaNacimiento, situacionRiesgoCrediticioId
					);
			if (riesgoCrediticioParaguay != null) {
				if (riesgoCrediticioParaguay.getEstadoRiesgoCrediticio().getId().equals(
						Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					)
					
//					&& !riesgoCrediticioParaguay.getCalificacionRiesgoCrediticioBCU().getId().equals(
//						Long.parseLong(
//							Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR")
//						)
//					)
					
					&& (
						riesgoCrediticioParaguay.getFechaVigenciaDesde() == null
						|| riesgoCrediticioParaguay.getFechaVigenciaDesde().after(fechaMaximaVigencia)
					)
				) {
					result = riesgoCrediticioParaguay;
				} else {
					result = iRiesgoCrediticioParaguayBean.controlarRiesgo(
						documento, fechaNacimiento, situacionRiesgoCrediticioId
					);
					result.getEstadoRiesgoCrediticio().setNombre("PENDIENTE");
				}
			} else {
				result = iRiesgoCrediticioParaguayBean.controlarRiesgo(
					documento, fechaNacimiento, situacionRiesgoCrediticioId
				);
				result.getEstadoRiesgoCrediticio().setNombre("PENDIENTE");
			}
			
			result.setEmpresa(null);
			result.setRespuestaExterna(null);
			
			result.setFact(null);
			result.setFcre(null);
			result.setId(null);
			result.setTerm(null);
			result.setUact(null);
			result.setUcre(null);
			
			result.getEstadoRiesgoCrediticio().setFact(null);
			result.getEstadoRiesgoCrediticio().setFcre(null);
			result.getEstadoRiesgoCrediticio().setTerm(null);
			result.getEstadoRiesgoCrediticio().setUact(null);
			result.getEstadoRiesgoCrediticio().setUcre(null);
			
			result.getSituacionRiesgoCrediticioParaguay().setFact(null);
			result.getSituacionRiesgoCrediticioParaguay().setFcre(null);
			result.getSituacionRiesgoCrediticioParaguay().setTerm(null);
			result.getSituacionRiesgoCrediticioParaguay().setUact(null);
			result.getSituacionRiesgoCrediticioParaguay().setUcre(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getSiguienteDocumentoParaControlar")
	@Produces("application/json")
	public RiesgoCrediticioParaguay getSiguienteDocumentoParaControlar() {
		RiesgoCrediticioParaguay result = null;
		
		try {
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
			
			result = iRiesgoCrediticioParaguayBean.getSiguienteDocumentoParaControlar();
			
			if (result != null) {
				result.setEmpresa(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/actualizarDatosRiesgoCrediticioParaguay")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void actualizarDatosRiesgoCrediticioParaguay(RiesgoCrediticioParaguay riesgoCrediticioParaguay) {
		try {
			System.out.println(riesgoCrediticioParaguay.getDocumento()
				+ riesgoCrediticioParaguay.getFechaNacimiento()
				+ riesgoCrediticioParaguay.getSituacionRiesgoCrediticioParaguay().getId());
			
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguayUpdated = 
				iRiesgoCrediticioParaguayBean.actualizarDatosRiesgoCrediticioParaguay(riesgoCrediticioParaguay);
			
			if (riesgoCrediticioParaguayUpdated != null) {
				if (Boolean.parseBoolean(Configuration.getInstance().getProperty("creditoAmigoPY.Notify"))) {
					notificarCreditoAmigoPY(riesgoCrediticioParaguayUpdated.getId());
				}
				
				/*
				// Si la condición del análisis de riesgo resulta "pre-aprobado".
				if (riesgoCrediticioParaguayUpdated.getCondicion().equals(
					Configuration.getInstance().getProperty("creditoAmigoPY.CondicionBICSA")
				)) {
					IBICSABean iBICSABean = lookupBICSABean();
					
					// Obtener los datos del servicio web de BICSA.
					Persona persona = iBICSABean.obtenerPersona(
						Integer.parseInt(Configuration.getInstance().getProperty("creditoAmigoPY.TipoDocumentoTodosBICSA")),
						Configuration.getInstance().getProperty("creditoAmigoPY.PaisTodosBICSA"), 
						riesgoCrediticioParaguayUpdated.getDocumento(), 
						Configuration.getInstance().getProperty("creditoAmigoPY.InstitucionBICSA")
					);
					
					StringWriter stringWriter = new StringWriter();
					
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.writeValue(stringWriter, persona);
					
					// Grabar datos.
					Date hoy = GregorianCalendar.getInstance().getTime();
					
					RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA =
						new RiesgoCrediticioParaguayBICSA();
					
					riesgoCrediticioParaguayBICSA.setDatos(stringWriter.toString());
					riesgoCrediticioParaguayBICSA.setFact(hoy);
					riesgoCrediticioParaguayBICSA.setFcre(hoy);
					riesgoCrediticioParaguayBICSA.setRiesgoCrediticioParaguay(riesgoCrediticioParaguayUpdated);
					riesgoCrediticioParaguayBICSA.setTerm(Long.valueOf(1));
					riesgoCrediticioParaguayBICSA.setUact(Long.valueOf(1));
					riesgoCrediticioParaguayBICSA.setUcre(Long.valueOf(1));
					
					IRiesgoCrediticioParaguayBICSABean iRiesgoCrediticioParaguayBICSABean = 
						lookupRiesgoCrediticioParaguayBICSABean();
					
					iRiesgoCrediticioParaguayBICSABean.save(riesgoCrediticioParaguayBICSA);
					
					// Enviar los datos al servicio web de SystemMaster.
					
					WsBICSA wsBICSA = new WsBICSA();
					WsBICSASoap wsBICSASoap = wsBICSA.getWsBICSASoap12();
					
					wsBICSASoap.bicsaAdd(
						Integer.parseInt(riesgoCrediticioParaguayUpdated.getDocumento()), 
						stringWriter.toString()
					);
				}
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/notificarCreditoAmigoPY/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoNotificarCreditoAmigoPYTO notificarCreditoAmigoPY(@PathParam("id") Long id) {
		ResultadoNotificarCreditoAmigoPYTO result = new ResultadoNotificarCreditoAmigoPYTO();
		
		try {
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguay =
				iRiesgoCrediticioParaguayBean.getById(id);
			
			String keystorePath = 
				Configuration.getInstance().getProperty("keystore.path");
			String keystorePass =
				Configuration.getInstance().getProperty("keystore.pass");
			String urlString = 
				Configuration.getInstance().getProperty("creditoAmigoPY.URL");
			String creditoAmigoPYUser = 
				Configuration.getInstance().getProperty("creditoAmigoPY.user");
			String creditoAmigoPYPass = 
				Configuration.getInstance().getProperty("creditoAmigoPY.pass");
			
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
			
//			URL url = new URL(urlString);
//			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			HttpsURLConnection urlConnection = 
				(HttpsURLConnection) new URL(urlString).openConnection();
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
			
			String encodedCredential = 
				Base64.getEncoder().encodeToString(
					(creditoAmigoPYUser + ":" + creditoAmigoPYPass).getBytes()
				);
//			urlConnection.setRequestProperty("Authorization", "Basic Y3JlZGl0b2FtaWdvOlNDdFN5N3pBc01MM2NKTFBYWlYyazJENA==");
			urlConnection.setRequestProperty("Authorization", "Basic " + encodedCredential);
			
			String jsonInputString = 
				"{"
					+ "	\"document\": \"" + riesgoCrediticioParaguay.getDocumento()
					+ "\","
					+ " \"status\": \"" + 
						Configuration.getInstance().getProperty("riesgoCrediticioPY.status.PROCESSED")
					+ "\""
				+ "}";
			
			System.out.println(jsonInputString);
			
			byte[] input = jsonInputString.getBytes("utf-8");
			
			urlConnection.connect();
			
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(input, 0, input.length);
			
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
			
			System.out.println(response.toString());
			
			urlConnection.disconnect();
			
			result.setRespuestaExterna(response.toString());
			
			iRiesgoCrediticioParaguayBean.actualizarRespuestaExterna(id, result.getRespuestaExterna());
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespuestaExterna("No se recibió respuesta externa.");
		}
		
		return result;
	}
	
	@POST
	@Path("/exportarAExcel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcel(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
				
				String nombreArchivo = 
					iRiesgoCrediticioParaguayBean.exportarAExcel(metadataConsulta, usuarioId);
				
				if (nombreArchivo != null) {
					result = new ResultadoExportacionArchivoTO();
					result.setNombreArchivo(nombreArchivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/procesarArchivo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoImportacionArchivoTO procesarArchivo(
		ImportacionArchivoRiesgoCrediticioParaguayTO importacionArchivoRiesgoCrediticioParaguayTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iRiesgoCrediticioParaguayBean.procesarArchivoEmpresa(
						importacionArchivoRiesgoCrediticioParaguayTO.getNombre(),
						importacionArchivoRiesgoCrediticioParaguayTO.getEmpresaId(),
						usuarioId
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRiesgoCrediticioParaguayBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioParaguayBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioParaguayBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioParaguayBean) context.lookup(lookupName);
	}
}