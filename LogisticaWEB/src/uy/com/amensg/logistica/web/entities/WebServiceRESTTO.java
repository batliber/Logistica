package uy.com.amensg.logistica.web.entities;

public class WebServiceRESTTO {

	private String method;
	private String uri;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		WebServiceRESTTO that = (WebServiceRESTTO) o;
		
		if (method != null ? !method.equals(that.method) : that.method != null) return false;
		if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = method != null ? method.hashCode() : 0;
		result = 31 * result + (uri != null ? uri.hashCode() : 0);
		return result;
	}
}