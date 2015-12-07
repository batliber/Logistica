package uy.com.amensg.logistica.servlets;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import uy.com.amensg.logistica.bean.ContratoRoutingHistoryBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryBean;
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
			
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			String result = iContratoRoutingHistoryBean.preprocesarArchivoEmpresa(
				fileName,
				empresaId
			);
			
			request.setAttribute("message", result);
			request.setAttribute("fileName", fileName);
			request.setAttribute("empresaId", empresaId);
			
			request.getRequestDispatcher(caller).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("message", "No se ha podido completar la operación.");
			
			request.getRequestDispatcher(caller).forward(request, response);
		}
	}
	
	private IContratoRoutingHistoryBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoRoutingHistoryBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoRoutingHistoryBean) context.lookup(lookupName);
	}
}