package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(
	targetNamespace = "http://www.antel.com.uy/pi/notification/nbr-v1", 
	name = "PullPoint"
)
@XmlSeeAlso({
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, 
	ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1.ObjectFactory.class, 
	uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.ObjectFactory.class
})
@SOAPBinding(
	parameterStyle = SOAPBinding.ParameterStyle.BARE
)
public interface PullPoint {

	@WebMethod(
		operationName = "GetMessages", 
		action = "http://docs.oasis-open.org/wsn/bw-2/PullPoint/GetMessagesRequest"
	)
	@WebResult(
		name = "GetMessagesResponse", 
		targetNamespace = "http://www.antel.com.uy/pi/notification/nbr-v1", 
		partName = "GetMessagesResponse"
	)
	public GetMessagesResponse getMessages(
		@WebParam(
			partName = "GetMessagesRequest", 
			name = "GetMessages", 
			targetNamespace = "http://docs.oasis-open.org/wsn/b-2"
		)
		uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.GetMessages getMessagesRequest
	);
}