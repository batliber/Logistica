package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.pruebas.notify_test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class WSAHandler implements SOAPHandler<SOAPMessageContext> {
		
	private static final String addressingNS = "http://www.w3.org/2005/08/addressing";
	private static final String addressingPrefix = "wsa";
	
	private String to;
	private String from;
	private String messageID;
	private String wsaAction;
	
	private List<NS> nsList;

	private List<String> acks;

	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}
	
	public void setTo(String to) {
		this.to = to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public void setNamespace(List<NS> nsList) {
		this.nsList = nsList;
	}

	public void setACKs(List<String> acks) {
		this.acks = acks;
	}
	
	public void setWsaAction(String wsaAction) {
		this.wsaAction = wsaAction;
	}

	public boolean handleMessage(SOAPMessageContext messageContext) {
		Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {
    		if (!outboundProperty.booleanValue()) {
    			
    			return true;
    		}
    		
    		SOAPMessage message = messageContext.getMessage();
    		javax.xml.soap.SOAPEnvelope soapEnv = message.getSOAPPart().getEnvelope();
    		
            javax.xml.soap.SOAPHeader header = soapEnv.getHeader();
            
            if (header == null) {
            	header = soapEnv.addHeader();
            }
            header.setPrefix(soapEnv.getPrefix());
    		SOAPElement element = message.getSOAPHeader().addChildElement("To", addressingPrefix, addressingNS);
    		
    		if (nsList != null) {
	    		for (NS ns : nsList) {
	        		element.addNamespaceDeclaration(ns.getPrefix(), ns.getNamespace());
				}
    		}
    		
    		element.addTextNode(to);
    		element = message.getSOAPHeader().addChildElement("MessageID", addressingPrefix, addressingNS);
    		element.addTextNode(messageID);
    		element = message.getSOAPHeader().addChildElement("From", addressingPrefix, addressingNS);
    		element = element.addChildElement("Address", addressingPrefix, addressingNS);
    		element.addTextNode(from);
    		element = message.getSOAPHeader().addChildElement("Action", addressingPrefix, addressingNS);
    		element.addTextNode(wsaAction);
    		
    		if (acks != null) {
    			element = message.getSOAPHeader().addChildElement("ack", "notif", "http://www.antel.com.uy/pi/Notifications-v1.0");
    			for (String uuid : acks) {
    				SOAPElement uuidElem = element.addChildElement("uuid", "notif", "http://www.antel.com.uy/pi/Notifications-v1.0");
    				uuidElem.addTextNode(uuid);
				}
    		}

    		message.saveChanges();

    		return true;
        } catch (Exception e) {
        	
        }
    	return false;
	}

	public boolean handleFault(SOAPMessageContext messageContext) {
		return true;
	}

	public void close(MessageContext messageContext) {		
	}
}