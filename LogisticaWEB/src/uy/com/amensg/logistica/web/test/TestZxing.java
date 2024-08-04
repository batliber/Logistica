package uy.com.amensg.logistica.test;

import java.io.File;
import java.io.FileOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class TestZxing {

	public static void main(String[] args) {
		BitMatrix bitMatrix;
		com.google.zxing.Writer writer = new QRCodeWriter();
		try {
			//  Write Barcode
			bitMatrix = new Code128Writer().encode("123456789", BarcodeFormat.CODE_128, 150, 80, null);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("E:\\code12kk8_123456789.png")));
			System.out.println("Code128 Barcode Generated.");
			//  Write QR Code
			bitMatrix = writer.encode("123456789", BarcodeFormat.QR_CODE, 200, 200);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("E:\\qrcode_123456789.png")));
			System.out.println("QR Code Generated.");
			//  Write PDF417
			writer = new PDF417Writer();
			bitMatrix = writer.encode("123456789", BarcodeFormat.PDF_417, 80, 150);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File("E:\\pdf417_123456789.png")));
			System.out.println("PDF417 Code Generated.");
		} catch (Exception e) {
			System.out.println("Exception Found." + e.getMessage());
		}
	}
}