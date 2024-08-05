
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.299-03:00
 * Generated source version: 3.4.3
 */

@WebFault(name = "InvalidProducerPropertiesExpressionFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class InvalidProducerPropertiesExpressionFault extends Exception {

    private static final long serialVersionUID = 6346042261043412379L;
    
	private uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType faultInfo;

    public InvalidProducerPropertiesExpressionFault() {
        super();
    }

    public InvalidProducerPropertiesExpressionFault(String message) {
        super(message);
    }

    public InvalidProducerPropertiesExpressionFault(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public InvalidProducerPropertiesExpressionFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault) {
        super(message);
        this.faultInfo = invalidProducerPropertiesExpressionFault;
    }

    public InvalidProducerPropertiesExpressionFault(String message, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = invalidProducerPropertiesExpressionFault;
    }

    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType getFaultInfo() {
        return this.faultInfo;
    }
}
