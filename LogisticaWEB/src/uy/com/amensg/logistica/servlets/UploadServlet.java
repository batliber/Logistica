package uy.com.amensg.logistica.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import uy.com.amensg.logistica.bean.ActivacionBean;
import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.ControlBean;
import uy.com.amensg.logistica.bean.EmpresaBean;
import uy.com.amensg.logistica.bean.FormaPagoBean;
import uy.com.amensg.logistica.bean.IActivacionBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.bean.IControlBean;
import uy.com.amensg.logistica.bean.IEmpresaBean;
import uy.com.amensg.logistica.bean.IFormaPagoBean;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.util.Configuration;

@MultipartConfig(
	fileSizeThreshold=1024*1024*10,	// 10 MB
	maxFileSize=1024*1024*50,		// 50 MB
	maxRequestSize=1024*1024*100	// 100 MB
)
public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6189664872735800452L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String caller = request.getParameter("caller");
		
		try {
			if (caller.contains("mobile")) {
				processMobileRequest(request, response);
			} else if (caller.contains("ventas")
				|| caller.contains("monitoreo")) {
				processContratoUploadRequest(request, response);
			} else if (caller.contains("empresa")) {
				processEmpresasRequest(request, response);
			} else if (caller.contains("activaciones")) {
				processActivacionesRequest(request, response);
			} else if (caller.contains("controles")) {
				processControlesRequest(request, response);
			} else if (caller.contains("riesgo_crediticio")) {
				processControlRiesgoCrediticioRequest(request, response);
			} else if (caller.contains("contrato_archivo_adjunto")) {
				processContratoArchivoAdjuntoRequest(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			String json = "{"
				+ "\"message\": \"No se ha podido completar la operación.\""
				+ "}";
			
			response.addHeader("Content-Type", "application/json");
			response.getWriter().write(json);
			response.getWriter().close();
		}
	}
	
	/**
	 * Procesamiento de informes de entrega de contratos.
	 * Necesario por el upload de imágenes de documentación
	 * 
	 * @param request HttpServletRequest con el contexto de la invocación.
	 * @param response HttpServletResponse con el contexto de retorno.
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processMobileRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long numeroTramite = new Long(request.getParameter("inputNumeroTramite").toString());

		Long resultadoEntregaDistribucionId = 
			(request.getParameter("selectResultadoEntregaDistribucion") != null && !request.getParameter("selectResultadoEntregaDistribucion").toString().isEmpty()) ?
				new Long(request.getParameter("selectResultadoEntregaDistribucion").toString()) :
				null;
		String resultadoEntregaDistribucionObservaciones = 
			(request.getParameter("textareaObservaciones") != null && !request.getParameter("textareaObservaciones").toString().isEmpty()) ?
				request.getParameter("textareaObservaciones") :
				null;
		Double resultadoEntregaDistribucionLatitud =
			(request.getParameter("inputLatitud") != null && !request.getParameter("inputLatitud").toString().isEmpty()) ? 
				new Double(request.getParameter("inputLatitud").toString()) :
				null;
		Double resultadoEntregaDistribucionLongitud =
			(request.getParameter("inputLongitud") != null && !request.getParameter("inputLongitud").toString().isEmpty()) ?
				new Double(request.getParameter("inputLongitud").toString())
				: null;
		Double resultadoEntregaDistribucionPrecision = 
			(request.getParameter("inputPrecision") != null && !request.getParameter("inputPrecision").toString().isEmpty()) ?
				new Double(request.getParameter("inputPrecision").toString())
				: null;
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringDate = format.format(date);
		
		HttpSession httpSession = request.getSession(false);
		Long loggedUsuarioId = new Long(httpSession.getAttribute("sesion").toString());
		
		IContratoBean iContratoBean = lookupContratoBean();
		
		Contrato contrato = iContratoBean.getByNumeroTramite(numeroTramite);

		Set<ContratoArchivoAdjunto> archivosAdjuntos = new LinkedHashSet<ContratoArchivoAdjunto>();
		archivosAdjuntos.addAll(contrato.getArchivosAdjuntos());
		
		int count = 0;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
			for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
	        		String fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
	        		
	        		part.write(
						Configuration.getInstance().getProperty("importacion.carpeta") + stringDate + "_" + contrato.getMid() + "_" + fileName
					);
	        		
	        		ContratoArchivoAdjunto contratoArchivoAdjunto = new ContratoArchivoAdjunto();
	        		contratoArchivoAdjunto.setContrato(contrato);
	        		contratoArchivoAdjunto.setFechaSubida(date);
	        		contratoArchivoAdjunto.setUrl(stringDate + "_" + contrato.getMid() + "_" + fileName);
	        		
	        		contratoArchivoAdjunto.setFact(date);
	        		contratoArchivoAdjunto.setTerm(new Long(1));
	        		contratoArchivoAdjunto.setUact(loggedUsuarioId);
	        		
	        		archivosAdjuntos.add(contratoArchivoAdjunto);
	        		
	        		if (count == 0) {
	        			contrato.setResultadoEntregaDistribucionURLAnverso(
        					stringDate + "_" + contrato.getMid() + "_" + fileName
        				);
	        		} else if (count == 1) {
	        			contrato.setResultadoEntregaDistribucionURLReverso(
        					stringDate + "_" + contrato.getMid() + "_" + fileName
        				);
	        		}
	        		
	        		count++;
	        		
	        		break;
        		}
			}
		}
		
		contrato.setArchivosAdjuntos(archivosAdjuntos);
		
		if (resultadoEntregaDistribucionObservaciones != null) {
			contrato.setResultadoEntregaDistribucionObservaciones(resultadoEntregaDistribucionObservaciones);
		}
		
		if (resultadoEntregaDistribucionLatitud != null) {
			contrato.setResultadoEntregaDistribucionLatitud(resultadoEntregaDistribucionLatitud);
		}
		
		if (resultadoEntregaDistribucionLongitud != null) {
			contrato.setResultadoEntregaDistribucionLongitud(resultadoEntregaDistribucionLongitud);
		}
		
		if (resultadoEntregaDistribucionPrecision != null) {
			contrato.setResultadoEntregaDistribucionPrecision(resultadoEntregaDistribucionPrecision);
		}
		
		contrato.setFact(date);
		contrato.setUact(loggedUsuarioId);
		contrato.setTerm(new Long(1));
		
		if (resultadoEntregaDistribucionId != null) {
			ResultadoEntregaDistribucion resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
			resultadoEntregaDistribucion.setId(resultadoEntregaDistribucionId);
			
			contrato.setResultadoEntregaDistribucion(resultadoEntregaDistribucion);
		}
		
		iContratoBean.update(contrato);
		
		String json = "{"
			+ "\"message\": \"Formulario enviado correctamente.\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	/**
	 * Procesamiento de archivos de importación de contratos para empresas.
	 * Requiere un archivo .csv con los datos a importar.
	 * 
	 * @param request HttpServletRequest con el contexto de la invocación.
	 * @param response HttpServletResponse con el contexto de retorno.
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processContratoUploadRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = new Long(request.getParameter("selectEmpresa").toString());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		String stringNow = format.format(GregorianCalendar.getInstance().getTime());
		
		String fileName = null;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
	        for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
            		fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
            		break;
            	}
	        }
	        
	        if (fileName != null) {
	        	fileName = stringNow + "_" + fileName;
	        	
	        	part.write(Configuration.getInstance().getProperty("importacion.carpeta") + fileName);
	        	break;
	        }
		}
		
		IContratoBean iContratoBean = lookupContratoBean();
		
		String result = iContratoBean.preprocesarArchivoEmpresa(
			fileName,
			empresaId
		);
		
		request.setAttribute("message", result);
		request.setAttribute("fileName", fileName);
		request.setAttribute("empresaId", empresaId);
		
		String json = "{"
			+ "\"message\": \"" + result + "\","
			+ "\"fileName\": \"" + fileName + "\","
			+ "\"empresaId\": \"" + empresaId + "\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}

	/**
	 * Procesamiento de alta y modificación de Empresas.
	 * Necesario por el upload del logo de la empresa.
	 * 
	 * @param request HttpServletRequest con el contexto de la invocación.
	 * @param response HttpServletResponse con el contexto de retorno.
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processEmpresasRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = !request.getParameter("inputEmpresaId").toString().equals("") ? 
			new Long(request.getParameter("inputEmpresaId").toString()) : null;
		String empresaNombre = request.getParameter("inputEmpresaNombre").toString();
		Long empresaCodigoPromotor = !request.getParameter("inputEmpresaCodigoPromotor").toString().equals("") ? 
			new Long(request.getParameter("inputEmpresaCodigoPromotor").toString()) : null;
		String empresaNombreContrato = request.getParameter("inputEmpresaNombreContrato").toString();
		String empresaNombreSucursal = request.getParameter("inputEmpresaNombreSucursal").toString();
		
		IFormaPagoBean iFormaPagoBean = lookupFormaPagoBean();
		
		Collection<FormaPago> formaPagos = new LinkedList<FormaPago>();
		for (FormaPago formaPago : iFormaPagoBean.list()) {
			String inputFormaPagoValue = request.getParameter("inputFormaPago" + formaPago.getId());
			
			if (inputFormaPagoValue != null && !inputFormaPagoValue.equals("")) {
				formaPagos.add(formaPago);
			}
		}
		
		String fileName = null;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
	        for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
            		fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
            		break;
            	}
	        }
	        
	        if (fileName != null && !fileName.equals("")) {
	        	part.write(Configuration.getInstance().getProperty("importacion.carpeta") + fileName);
	        	break;
	        }
		}
		
		HttpSession httpSession = request.getSession(false);
		Long loggedUsuarioId = new Long(httpSession.getAttribute("sesion").toString());
		
		IEmpresaBean iEmpresaBean = lookupEmpresaBean();
		
		Empresa empresa = null;
		if (empresaId != null) {
			empresa = iEmpresaBean.getById(empresaId);
		} else {
			empresa = new Empresa();
		}
		
		if (fileName != null && !fileName.equals("")) {
			empresa.setLogoURL(fileName);
		}
		
		empresa.setNombre(empresaNombre);
		empresa.setCodigoPromotor(empresaCodigoPromotor);
		empresa.setNombreContrato(empresaNombreContrato);
		empresa.setNombreSucursal(empresaNombreSucursal);
		
		empresa.setFormaPagos(formaPagos);
		
		empresa.setFact(GregorianCalendar.getInstance().getTime());
		empresa.setTerm(new Long(1));
		empresa.setUact(loggedUsuarioId);
		
		if (empresa.getId() != null) {
			iEmpresaBean.update(empresa);
		} else {
			iEmpresaBean.save(empresa);
		}
		
		String json = "{"
			+ "\"message\": \"Operación exitosa.\","
			+ "\"fileName\": \"" + empresa.getLogoURL() + "\","
			+ "\"empresaId\": \"" + empresaId + "\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	/**
	 * Procesamiento de archivos de importación de activaciones para empresas.
	 * Requiere un archivo .csv con los datos a importar.
	 * 
	 * @param request HttpServletRequest con el contexto de la invocación.
	 * @param response HttpServletResponse con el contexto de retorno.
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processActivacionesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = new Long(request.getParameter("selectEmpresa").toString());
		Long tipoActivacionId = new Long(request.getParameter("selectTipoActivacion").toString());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		String fileName = null;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
	        for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
            		fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
            		break;
            	}
	        }
	        
	        if (fileName != null) {
	        	fileName = format.format(date) + "_" + fileName;
	        	
	        	part.write(Configuration.getInstance().getProperty("importacion.carpeta") + fileName);
	        	break;
	        }
		}
		
		IActivacionBean iActivacionBean = lookupActivacionBean();
		
		String result = iActivacionBean.preprocesarArchivoEmpresa(
			fileName,
			empresaId
		);
		
		request.setAttribute("message", result);
		request.setAttribute("fileName", fileName);
		request.setAttribute("empresaId", empresaId);
		request.setAttribute("tipoActivacionId", tipoActivacionId);
		
		String json = "{"
			+ "\"message\": \"" + result + "\","
			+ "\"fileName\": \"" + fileName + "\","
			+ "\"empresaId\": \"" + empresaId + "\","
			+ "\"tipoActivacionId\": \"" + tipoActivacionId + "\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	/**
	 * Procesamiento de archivos de importación de controles para empresas.
	 * Requiere un archivo .csv con los datos a importar.
	 * 
	 * @param request HttpServletRequest con el contexto de la invocación.
	 * @param response HttpServletResponse con el contexto de retorno.
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processControlesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = new Long(request.getParameter("selectEmpresa").toString());
		Long tipoControlId = new Long(request.getParameter("selectTipoControl").toString());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		String fileName = null;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
	        for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
            		fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
            		break;
            	}
	        }
	        
	        if (fileName != null) {
	        	fileName = format.format(date) + "_" + fileName;
	        	
	        	part.write(Configuration.getInstance().getProperty("importacion.carpeta") + fileName);
	        	break;
	        }
		}
		
		IControlBean iControlBean = lookupControlBean();
		
		String result = iControlBean.preprocesarArchivoEmpresa(
			fileName,
			empresaId
		);
		
		request.setAttribute("message", result);
		request.setAttribute("fileName", fileName);
		request.setAttribute("empresaId", empresaId);
		request.setAttribute("tipoControlId", tipoControlId);
		
		String json = "{"
			+ "\"message\": \"" + result + "\","
			+ "\"fileName\": \"" + fileName + "\","
			+ "\"empresaId\": \"" + empresaId + "\","
			+ "\"tipoControlId\": \"" + tipoControlId + "\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	/**
	 * Procesamiento de archivos de importación de controles de riesgo crediticio para empresas.
	 * Requiere un archivo .csv con los datos a importar.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processControlRiesgoCrediticioRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = new Long(request.getParameter("selectEmpresa").toString());
		Long tipoControlRiesgoCrediticioId = new Long(request.getParameter("selectTipoControlRiesgoCrediticio").toString());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		String fileName = null;
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
	        for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
            		fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
            		break;
            	}
	        }
	        
	        if (fileName != null) {
	        	fileName = format.format(date) + "_" + fileName;
	        	
	        	part.write(Configuration.getInstance().getProperty("importacion.carpeta") + fileName);
	        	break;
	        }
		}
		
		IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
		
		String result = iRiesgoCrediticioBean.preprocesarArchivoEmpresa(
			fileName,
			empresaId
		);
		
		request.setAttribute("message", result);
		request.setAttribute("fileName", fileName);
		request.setAttribute("empresaId", empresaId);
		request.setAttribute("tipoControlRiesgoCrediticioId", tipoControlRiesgoCrediticioId);
		
		String json = "{"
			+ "\"message\": \"" + result + "\","
			+ "\"fileName\": \"" + fileName + "\","
			+ "\"empresaId\": \"" + empresaId + "\","
			+ "\"tipoControlRiesgoCrediticioId\": \"" + tipoControlRiesgoCrediticioId + "\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	/**
	 * Procesamiento de archivos de adjuntos de contratos.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws NamingException
	 */
	private void processContratoArchivoAdjuntoRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long id = new Long(request.getParameter("inputId").toString());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringDate = format.format(date);
		
		HttpSession httpSession = request.getSession(false);
		Long loggedUsuarioId = new Long(httpSession.getAttribute("sesion").toString());
		
		IContratoBean iContratoBean = lookupContratoBean();
		
		Contrato contrato = iContratoBean.getById(id);

		Set<ContratoArchivoAdjunto> archivosAdjuntos = new LinkedHashSet<ContratoArchivoAdjunto>();
		archivosAdjuntos.addAll(contrato.getArchivosAdjuntos());
		
		for (Part part : request.getParts()) {
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
			for (String token : tokens) {
				if (token.trim().startsWith("filename")) {
	        		String fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
	        		
	        		part.write(
						Configuration.getInstance().getProperty("importacion.carpeta") + stringDate + "_" + contrato.getMid() + "_" + fileName
					);
	        		
	        		ContratoArchivoAdjunto contratoArchivoAdjunto = new ContratoArchivoAdjunto();
	        		contratoArchivoAdjunto.setContrato(contrato);
	        		contratoArchivoAdjunto.setFechaSubida(date);
	        		contratoArchivoAdjunto.setUrl(stringDate + "_" + contrato.getMid() + "_" + fileName);
	        		
	        		contratoArchivoAdjunto.setFact(date);
	        		contratoArchivoAdjunto.setTerm(new Long(1));
	        		contratoArchivoAdjunto.setUact(loggedUsuarioId);
	        		
	        		archivosAdjuntos.add(contratoArchivoAdjunto);
	        		
	        		break;
        		}
			}
		}
		
		contrato.setArchivosAdjuntos(archivosAdjuntos);
		
		contrato.setFact(date);
		contrato.setUact(loggedUsuarioId);
		contrato.setTerm(new Long(1));
		
		iContratoBean.update(contrato);
		
		String json = "{"
			+ "\"message\": \"Formulario enviado correctamente.\""
			+ "}";
		
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(json);
		response.getWriter().close();
	}
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoBean) context.lookup(lookupName);
	}

	private IEmpresaBean lookupEmpresaBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEmpresaBean) context.lookup(lookupName);
	}

	private IActivacionBean lookupActivacionBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IActivacionBean) context.lookup(lookupName);
	}
	
	private IControlBean lookupControlBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ControlBean.class.getSimpleName();
		String remoteInterfaceName = IControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IControlBean) context.lookup(lookupName);
	}
	
	private IRiesgoCrediticioBean lookupRiesgoCrediticioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}

	private IFormaPagoBean lookupFormaPagoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FormaPagoBean.class.getSimpleName();
		String remoteInterfaceName = IFormaPagoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IFormaPagoBean) context.lookup(lookupName);
	}
}