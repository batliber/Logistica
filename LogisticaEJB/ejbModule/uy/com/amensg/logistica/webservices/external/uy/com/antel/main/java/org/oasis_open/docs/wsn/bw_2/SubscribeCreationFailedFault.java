
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.299-03:00
 * Generated source version: 3.4.3
 */

@WebFault(name = "SubscribeCreationFailedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class SubscribeCreationFailedFault extends Exception {

    private static final long serialVersionUID = 3656522557067748426L;
    
	private uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType faultInfo;

    public SubscribeCreationFailedFault() {
        super();
    }

    public SubscribeCreationFailedFault(String message) {
        super(message);
    }

    public SubscribeCreationFailedFault(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public SubscribeCreationFailedFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType subscribeCreationFailedFault) {
        super(message);
        this.faultInfo = subscribeCreationFailedFault;
    }

    public SubscribeCreationFailedFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType subscribeCreationFailedFault, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = subscribeCreationFailedFault;
    }

    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}
