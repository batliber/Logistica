package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.brw_2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.346-03:00
 * Generated source version: 3.4.3
 *
 */
@WebService(targetNamespace = "http://docs.oasis-open.org/wsn/brw-2", name = "RegisterPublisher")
@XmlSeeAlso({
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ObjectFactory.class,
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.ObjectFactory.class
})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RegisterPublisher {

    @WebMethod(operationName = "RegisterPublisher")
    @WebResult(name = "RegisterPublisherResponse", targetNamespace = "http://docs.oasis-open.org/wsn/br-2", partName = "RegisterPublisherResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisher(

        @WebParam(partName = "RegisterPublisherRequest", name = "RegisterPublisher", targetNamespace = "http://docs.oasis-open.org/wsn/br-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest
    ) throws uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault, 
    	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault, 
    	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault, 
    	PublisherRegistrationFailedFault, 
    	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault, PublisherRegistrationRejectedFault;
}