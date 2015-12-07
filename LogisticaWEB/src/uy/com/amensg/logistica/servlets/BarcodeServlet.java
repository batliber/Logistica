package uy.com.amensg.logistica.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class BarcodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1846460863605332676L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = null;
		try {
			String code = request.getParameter("code");
			String type = request.getParameter("type");
			
			servletOutputStream = response.getOutputStream();
			
			BitMatrix bitMatrix;
			com.google.zxing.Writer writer = null;
			
			if (type != null) {
				if (type.equals("QR")) {
					//  Write QR Code
					writer = new QRCodeWriter();
					bitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, 200, 200);
					MatrixToImageWriter.writeToStream(bitMatrix, "png", servletOutputStream);
				} else if (type.equals("B")) {
					//  Write Barcode
					writer = new Code128Writer();
					bitMatrix = writer.encode(code, BarcodeFormat.CODE_128, 150, 80, null);
					MatrixToImageWriter.writeToStream(bitMatrix, "png", servletOutputStream);
				} else if (type.equals("PDF")) {
					//  Write PDF417
					writer = new PDF417Writer();
					bitMatrix = writer.encode(code, BarcodeFormat.PDF_417, 80, 150);
					MatrixToImageWriter.writeToStream(bitMatrix, "png", servletOutputStream);
				}
			} else {
				//  Write Barcode
				writer = new Code128Writer();
				bitMatrix = writer.encode(code, BarcodeFormat.CODE_128, 150, 80, null);
				MatrixToImageWriter.writeToStream(bitMatrix, "png", servletOutputStream);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(servletOutputStream != null) {
				servletOutputStream.close();
			}
		}
	}
}