package uy.com.amensg.logistica.web.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.com.amensg.logistica.util.Configuration;

public class StreamingServlet extends HttpServlet {

	private static final long serialVersionUID = 8696109936126827359L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fn");
		
		ServletOutputStream servletOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			bufferedInputStream = 
				new BufferedInputStream(
					new FileInputStream(
						Configuration.getInstance().getProperty("streaming.carpeta") + fileName
					)
				);
			
			int readBytes = 0;
			while((readBytes = bufferedInputStream.read()) != -1) {
				servletOutputStream.write(readBytes);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(servletOutputStream != null) {
				servletOutputStream.close();
			}
			if(bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}
	}
}