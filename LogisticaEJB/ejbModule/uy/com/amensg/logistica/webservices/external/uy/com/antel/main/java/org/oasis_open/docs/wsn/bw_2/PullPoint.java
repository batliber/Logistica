package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.bw_2;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-06-08T12:25:56.362-03:00
 * Generated source version: 3.4.3
 *
 */
@WebService(targetNamespace = "http://docs.oasis-open.org/wsn/bw-2", name = "PullPoint")
@XmlSeeAlso({uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1.ObjectFactory.class, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PullPoint {

    @WebMethod(operationName = "Notify")
    @Oneway
    public void notify(

        @WebParam(partName = "Notify", name = "Notify", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.Notify notify
    );

    @WebMethod(operationName = "DestroyPullPoint")
    @WebResult(name = "DestroyPullPointResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "DestroyPullPointResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.DestroyPullPointResponse destroyPullPoint(

        @WebParam(partName = "DestroyPullPointRequest", name = "DestroyPullPoint", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.DestroyPullPoint destroyPullPointRequest
    ) throws UnableToDestroyPullPointFault, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

    @WebMethod(operationName = "GetMessages")
    @WebResult(name = "GetMessagesResponse", targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "GetMessagesResponse")
    public uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.GetMessagesResponse getMessages(

        @WebParam(partName = "GetMessagesRequest", name = "GetMessages", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
        uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.GetMessages getMessagesRequest
    ) throws UnableToGetMessagesFault, uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
}
