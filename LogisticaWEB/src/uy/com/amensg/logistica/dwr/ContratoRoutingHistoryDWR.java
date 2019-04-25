package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoRoutingHistoryBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryBean;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.ContratoRoutingHistoryTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;

@RemoteProxy
public class ContratoRoutingHistoryDWR {

	private IContratoRoutingHistoryBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRoutingHistoryBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoRoutingHistoryBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iContratoRoutingHistoryBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object contratoRoutingHistory : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((ContratoRoutingHistory) contratoRoutingHistory));
//				for (Object contrato : metadataConsultaResultado.getRegistrosMuestra()) {
//					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<ContratoRoutingHistoryTO> listByContratoId(Long contratoId) {
		Collection<ContratoRoutingHistoryTO> result = new LinkedList<ContratoRoutingHistoryTO>();

		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			for (ContratoRoutingHistory contratoRoutingHistory : iContratoRoutingHistoryBean.listByContratoId(contratoId)) {
				result.add(transform(contratoRoutingHistory));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ContratoRoutingHistoryTO transform(ContratoRoutingHistory contratoRoutingHistory) {
		ContratoRoutingHistoryTO result = new ContratoRoutingHistoryTO();
		
		result.setFecha(contratoRoutingHistory.getFecha());
		
		result.setContrato(ContratoDWR.transform(contratoRoutingHistory.getContrato(), false));
		
		if (contratoRoutingHistory.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(contratoRoutingHistory.getEmpresa(), false));
		}
		
		if (contratoRoutingHistory.getRol() != null) {
			result.setRol(RolDWR.transform(contratoRoutingHistory.getRol(), false));
		}
		
		if (contratoRoutingHistory.getUsuario() != null) {
			result.setUsuario(UsuarioDWR.transform(contratoRoutingHistory.getUsuario(), false));
		}
		
		if (contratoRoutingHistory.getEstado() != null) {
			result.setEstado(EstadoDWR.transform(contratoRoutingHistory.getEstado()));
		}
		
		if (contratoRoutingHistory.getUsuarioAct() != null) {
			result.setUsuarioAct(UsuarioDWR.transform(contratoRoutingHistory.getUsuarioAct(), false));
		}
		
		result.setFcre(contratoRoutingHistory.getFcre());
		result.setFact(contratoRoutingHistory.getFact());
		result.setId(contratoRoutingHistory.getId());
		result.setTerm(contratoRoutingHistory.getTerm());
		result.setUact(contratoRoutingHistory.getUact());
		result.setUcre(contratoRoutingHistory.getUcre());
		
		return result;
	}

	public static ContratoRoutingHistory transform(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		ContratoRoutingHistory result = new ContratoRoutingHistory();
		
		result.setFecha(contratoRoutingHistoryTO.getFecha());
		
		result.setContrato(ContratoDWR.transform(contratoRoutingHistoryTO.getContrato()));
		
		if (contratoRoutingHistoryTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(contratoRoutingHistoryTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (contratoRoutingHistoryTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoRoutingHistoryTO.getRol().getId());
			
			result.setRol(rol);
		}
		
		if (contratoRoutingHistoryTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoRoutingHistoryTO.getUsuario().getId());
			
			result.setUsuario(usuario);
		}
		
		if (contratoRoutingHistoryTO.getEstado() != null) {
			Estado estado = new Estado();
			estado.setId(contratoRoutingHistoryTO.getEstado().getId());
			
			result.setEstado(estado);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(contratoRoutingHistoryTO.getFcre());
		result.setFact(date);
		result.setId(contratoRoutingHistoryTO.getId());
		result.setTerm(contratoRoutingHistoryTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(contratoRoutingHistoryTO.getUcre());
		
		return result;
	}
}