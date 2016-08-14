package uy.com.amensg.logistica.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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

import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.EmpresaBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.bean.IEmpresaBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.Empresa;
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
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String stringDate = format.format(date);
		
		IContratoBean iContratoBean = lookupContratoBean();
		
		Contrato contrato = iContratoBean.getByNumeroTramite(numeroTramite);
		
		for (Part part : request.getParts()) {
			String name = part.getName();
			String contentDisposition = part.getHeader("content-disposition");
			
			String[] tokens = contentDisposition.split(";");
			for (String token : tokens) {
	        	if (token.trim().startsWith("filename")) {
	        		String fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
	        		
	        		part.write(
						Configuration.getInstance().getProperty("importacion.carpeta") + stringDate + "_" + contrato.getMid() + "_" + fileName
					);
	        		
	        		if (name.contains("Anverso")) {
	        			contrato.setResultadoEntregaDistribucionURLAnverso(
        					stringDate + "_" + contrato.getMid() + "_" + fileName
        				);
	        		} else if (name.contains("Reverso")) {
	        			contrato.setResultadoEntregaDistribucionURLReverso(
        					stringDate + "_" + contrato.getMid() + "_" + fileName
        				);
	        		}
            		
	        		break;
        		}
			}
		}
		
		HttpSession httpSession = request.getSession(false);
		Long loggedUsuarioId = new Long(httpSession.getAttribute("sesion").toString());
		
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
	
	private void processContratoUploadRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = new Long(request.getParameter("selectEmpresa").toString());
		
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
	
	private void processEmpresasRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException {
		Long empresaId = !request.getParameter("inputEmpresaId").toString().equals("") ? 
			new Long(request.getParameter("inputEmpresaId").toString()) : null;
		String empresaNombre = request.getParameter("inputEmpresaNombre").toString();
		Long empresaCodigoPromotor = !request.getParameter("inputEmpresaCodigoPromotor").toString().equals("") ? 
			new Long(request.getParameter("inputEmpresaCodigoPromotor").toString()) : null;
		String empresaNombreContrato = request.getParameter("inputEmpresaNombreContrato").toString();
		String empresaNombreSucursal = request.getParameter("inputEmpresaNombreSucursal").toString();
		
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
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoBean) context.lookup(lookupName);
	}

	private IEmpresaBean lookupEmpresaBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = EmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEmpresaBean) context.lookup(lookupName);
	}
}