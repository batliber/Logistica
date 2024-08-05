package uy.com.amensg.logistica.web.test;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TestSOAPParsing {

	public TestSOAPParsing() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document d = 
	        	db.parse(
	        		new InputSource(
	        			new StringReader(
	        				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
	        				+ "<soap:Body>"
	        				+ "<ns2:addDataMaskResponse xmlns:ns2=\"http://boundary.core.apti.automatismos.asf.antel.uy/\">"
	        				+ "<return>0</return>"
	        				+ "</ns2:addDataMaskResponse>"
	        				+ "</soap:Body>"
	        				+ "</soap:Envelope>"
	        			)
	        		)
	        	);
	       
	        System.out.println(d.getElementsByTagName("return").item(0).getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TestSOAPParsing();
	}
}