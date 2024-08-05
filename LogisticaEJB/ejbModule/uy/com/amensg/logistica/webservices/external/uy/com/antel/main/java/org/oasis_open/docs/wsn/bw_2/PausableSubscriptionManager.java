package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.377-03:00
 * Generated source version: 3.4.3
 *
 */
@WebService(targetNamespace = "http://docs.oasis-open.org/wsn/bw-2", name = "PausableSubscriptionManager")
@XmlSeeAlso({uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PausableSubscriptionManager {

    @WebMethod(operationName = "Unsubscribe")
    @WebResult(name = "UnsubscribeResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "UnsubscribeResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribe(

        @WebParam(partName = "UnsubscribeRequest", name = "Unsubscribe", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest
    ) throws UnableToDestroySubscriptionFault, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

    @WebMethod(operationName = "PauseSubscription")
    @WebResult(name = "PauseSubscriptionResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "PauseSubscriptionResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse pauseSubscription(

        @WebParam(partName = "PauseSubscriptionRequest", name = "PauseSubscription", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscriptionRequest
    ) throws PauseFailedFault, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

    @WebMethod(operationName = "ResumeSubscription")
    @WebResult(name = "ResumeSubscriptionResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "ResumeSubscriptionResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse resumeSubscription(

        @WebParam(partName = "ResumeSubscriptionRequest", name = "ResumeSubscription", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscriptionRequest
    ) throws uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault, ResumeFailedFault;

    @WebMethod(operationName = "Renew")
    @WebResult(name = "RenewResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "RenewResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.RenewResponse renew(

        @WebParam(partName = "RenewRequest", name = "Renew", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.Renew renewRequest
    ) throws UnacceptableTerminationTimeFault, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
}
