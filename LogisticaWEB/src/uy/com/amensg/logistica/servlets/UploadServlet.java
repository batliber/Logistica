package uy.com.amensg.logistica.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

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
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.entities.Contrato;
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
				Long numeroTramite = new Long(request.getParameter("inputNumeroTramite").toString());
				Long resultadoEntregaDistribucionId = new Long(request.getParameter("selectResultadoEntregaDistribucion").toString());
				String resultadoEntregaDistribucionObservaciones = request.getParameter("textareaObservaciones").toString();
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
				
				Collection<String> fileNames = new LinkedList<String>();
				for (Part part : request.getParts()) {
					String contentDisposition = part.getHeader("content-disposition");
					
					String[] tokens = contentDisposition.split(";");
					for (String token : tokens) {
			        	if (token.trim().startsWith("filename")) {
			        		String fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
			        		fileNames.add(fileName);
			        		
			        		part.write(
								Configuration.getInstance().getProperty("importacion.carpeta") + stringDate + "_" + contrato.getMid() + "_" + fileName
							);
		            		
			        		break;
		        		}
					}
				}
				
				HttpSession httpSession = request.getSession(false);
				Long loggedUsuarioId = new Long(httpSession.getAttribute("sesion").toString());
				
				contrato.setResultadoEntregaDistribucionObservaciones(resultadoEntregaDistribucionObservaciones);
				
				String[] fileNamesArray = fileNames.toArray(new String[]{});
				
				contrato.setResultadoEntregaDistribucionURLAnverso(
					stringDate + "_" + contrato.getMid() + "_" + fileNamesArray[0]
				);
				contrato.setResultadoEntregaDistribucionURLReverso(
					stringDate + "_" + contrato.getMid() + "_" + fileNamesArray[1]
				);
				contrato.setResultadoEntregaDistribucionLatitud(resultadoEntregaDistribucionLatitud);
				contrato.setResultadoEntregaDistribucionLongitud(resultadoEntregaDistribucionLongitud);
				contrato.setResultadoEntregaDistribucionPrecision(resultadoEntregaDistribucionPrecision);
				
				contrato.setFact(date);
				contrato.setUact(loggedUsuarioId);
				contrato.setTerm(new Long(1));
				
				ResultadoEntregaDistribucion resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
				resultadoEntregaDistribucion.setId(resultadoEntregaDistribucionId);
				
				contrato.setResultadoEntregaDistribucion(resultadoEntregaDistribucion);
				
				iContratoBean.update(contrato);
				
				String json = "{"
					+ "\"message\": \"Formulario enviado correctamente.\""
					+ "}";
				
				response.addHeader("Content-Type", "application/json");
				response.getWriter().write(json);
				response.getWriter().close();
			} else {
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
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoBean) context.lookup(lookupName);
	}
}