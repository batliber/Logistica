
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.284-03:00
 * Generated source version: 3.4.3
 */

@WebFault(name = "NoCurrentMessageOnTopicFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class NoCurrentMessageOnTopicFault extends Exception {

    private static final long serialVersionUID = 6803560602278406381L;
    
	private uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType faultInfo;

    public NoCurrentMessageOnTopicFault() {
        super();
    }

    public NoCurrentMessageOnTopicFault(String message) {
        super(message);
    }

    public NoCurrentMessageOnTopicFault(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public NoCurrentMessageOnTopicFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType noCurrentMessageOnTopicFault) {
        super(message);
        this.faultInfo = noCurrentMessageOnTopicFault;
    }

    public NoCurrentMessageOnTopicFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType noCurrentMessageOnTopicFault, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = noCurrentMessageOnTopicFault;
    }

    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType getFaultInfo() {
        return this.faultInfo;
    }
}
