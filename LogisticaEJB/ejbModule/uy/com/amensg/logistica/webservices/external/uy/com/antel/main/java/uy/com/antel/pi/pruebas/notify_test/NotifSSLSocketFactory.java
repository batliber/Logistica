package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.pruebas.notify_test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NotifSSLSocketFactory extends SSLSocketFactory {
	
	private SSLSocketFactory sslSocketFactory = null;
	
	public NotifSSLSocketFactory() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}
			}};
			
			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			
			// Create empty HostnameVerifier
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			
			sslSocketFactory = sc.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		return sslSocketFactory.createSocket(s, host, port, autoClose);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return sslSocketFactory.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return sslSocketFactory.getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return sslSocketFactory.createSocket(host, port);
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return sslSocketFactory.createSocket(host, port);
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
		return sslSocketFactory.createSocket(host, port, localHost, localPort);
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
		return sslSocketFactory.createSocket(address, port, localAddress, localPort);
	}
}