package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
	}

	public AddDataMaskResponse createAddDataMaskResponse() {
		return new AddDataMaskResponse();
	}

	public GetDataMask createGetDataMask() {
		return new GetDataMask();
	}

	public GetDataMaskResponse createGetDataMaskResponse() {
		return new GetDataMaskResponse();
	}

	public AddDataMask createAddDataMask() {
		return new AddDataMask();
	}

	public DataMask createDataMask() {
		return new DataMask();
	}
}