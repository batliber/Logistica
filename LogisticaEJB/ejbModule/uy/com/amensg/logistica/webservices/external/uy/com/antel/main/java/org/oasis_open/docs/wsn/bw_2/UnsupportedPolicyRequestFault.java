
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.315-03:00
 * Generated source version: 3.4.3
 */

@WebFault(name = "UnsupportedPolicyRequestFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class UnsupportedPolicyRequestFault extends Exception {

    private static final long serialVersionUID = 7823405442000123601L;
    
	private uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType faultInfo;

    public UnsupportedPolicyRequestFault() {
        super();
    }

    public UnsupportedPolicyRequestFault(String message) {
        super(message);
    }

    public UnsupportedPolicyRequestFault(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPolicyRequestFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType unsupportedPolicyRequestFault) {
        super(message);
        this.faultInfo = unsupportedPolicyRequestFault;
    }

    public UnsupportedPolicyRequestFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType unsupportedPolicyRequestFault, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = unsupportedPolicyRequestFault;
    }

    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType getFaultInfo() {
        return this.faultInfo;
    }
}
